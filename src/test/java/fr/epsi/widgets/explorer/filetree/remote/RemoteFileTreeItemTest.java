package fr.epsi.widgets.explorer.filetree.remote;

import fr.epsi.controller.MainController;
import fr.epsi.service.command.CommandService;
import fr.epsi.service.command.commands.CdCommand;
import fr.epsi.service.command.commands.FTPCommand;
import fr.epsi.widgets.explorer.FileTreeItemTest;
import javafx.scene.control.TreeItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.PriorityQueue;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MainController.class)
public class RemoteFileTreeItemTest extends FileTreeItemTest {

    public static final String HOSTNAME = "server";

    private CommandService mockedCommandService;
    private CdCommand mockedFtpCommand;
    private Queue<FTPCommand> mockedCommandQueue = Mockito.spy(new PriorityQueue<FTPCommand>());

    public RemoteFileTreeItemTest()
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        super(RemoteFileTreeItem.class);
    }

    @Before
    public void setUp() throws Exception {
        testRoots = new File[1];

        File firstDirectoryMock = Mockito.spy(new File("test-directory-1"));
        Mockito.doReturn(true).when(firstDirectoryMock).isDirectory();
        testRoots[0] = firstDirectoryMock;

        fileTreeItem = Mockito.spy(new RemoteFileTreeItem(testRoots[0]));
        Mockito.doReturn(mockedChildren).when(fileTreeItem).getChildren();

        rootNode = new TreeItem<String>(HOSTNAME);
        rootNode.getChildren().add(fileTreeItem);

        mockedFtpCommand = Mockito.spy(new CdCommand(fileTreeItem.getValue()));
        Mockito.doReturn(mockedFtpCommand).when((RemoteFileTreeItem) fileTreeItem).createCdCommand();

        Mockito.doReturn(false).doReturn(true).when(mockedCommandQueue).contains(mockedFtpCommand);

        mockedCommandService = Mockito.mock(CommandService.class);
        Mockito.doReturn(mockedCommandQueue).when(mockedCommandService).getCommandQueue();

        PowerMockito.mockStatic(MainController.class);
        PowerMockito.when(MainController.getCommandService()).thenReturn(mockedCommandService);
        PowerMockito.when(MainController.isSocketConnected()).thenReturn(true);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGenerateChildNodesWithConnection() throws Exception {

        File responseDirectory = Mockito.spy(new File("test-directory-1"));
        Mockito.doReturn(new File[] { new File("test-file-1"), new File("test-file-2") }).when(responseDirectory).listFiles();

        Mockito.doReturn(responseDirectory).when(mockedFtpCommand).getResponse();

        fileTreeItem.generateChildNodes();

        Mockito.verify(mockedCommandQueue, Mockito.atLeast(1)).contains(mockedFtpCommand);
        Mockito.verify(mockedCommandQueue).offer(mockedFtpCommand);

        assertEquals(responseDirectory.toString(), fileTreeItem.getValue());
        assertEquals(2, fileTreeItem.getChildren().size());
    }

    @Test
    public void testGenerateChildNodesWithoutConnection() throws Exception {
        PowerMockito.when(MainController.isSocketConnected()).thenReturn(false);

        fileTreeItem.generateChildNodes();

        assertEquals(0, fileTreeItem.getChildren().size());
    }
}