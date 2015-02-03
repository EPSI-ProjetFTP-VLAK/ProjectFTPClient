package fr.epsi.widgets.explorer.filetree.remote;

import fr.epsi.controller.MainController;
import fr.epsi.dto.FileDTO;
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

        fileTreeItem = Mockito.spy(new RemoteFileTreeItem(new FileDTO(testRoots[0])));
        Mockito.doReturn(mockedChildren).when(fileTreeItem).getChildren();
        Mockito.doReturn(true).when((RemoteFileTreeItem) fileTreeItem).isSocketConnect();

        rootNode = new TreeItem<String>(HOSTNAME);
        rootNode.getChildren().add(fileTreeItem);

        mockedFtpCommand = Mockito.spy(new CdCommand(fileTreeItem.getValue()));
        Mockito.doReturn(mockedFtpCommand).when((RemoteFileTreeItem) fileTreeItem).createCdCommand();

        Mockito.doReturn(true).doReturn(false).when(mockedCommandQueue).contains(mockedFtpCommand);

        mockedCommandService = Mockito.mock(CommandService.class);
        Mockito.doReturn(mockedCommandQueue).when(mockedCommandService).getCommandQueue();

        PowerMockito.mockStatic(MainController.class);
        PowerMockito.when(MainController.getCommandService()).thenReturn(mockedCommandService);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGenerateChildNodesWithConnection() throws Exception {

        File responseDirectory = Mockito.spy(new File("test-directory-1"));
        Mockito.doReturn(true).when(responseDirectory).isDirectory();

        FileDTO[] responseDirectoriesDTO = new FileDTO[] { new FileDTO(responseDirectory, true), new FileDTO(new File("test-file-1"), false) };

        Mockito.doReturn(responseDirectoriesDTO).when(mockedFtpCommand).getResponse();

        Mockito.doReturn(false).doReturn(true).when(mockedFtpCommand).isExecuted();

        fileTreeItem.generateChildNodes();

        Mockito.verify(mockedFtpCommand, Mockito.atLeast(1)).isExecuted();
        Mockito.verify(mockedCommandQueue).offer(mockedFtpCommand);

        assertEquals(responseDirectory.toString(), fileTreeItem.getValue());
        assertEquals(2, fileTreeItem.getChildren().size());
    }

    @Test
    public void testGenerateChildNodesWithoutConnection() throws Exception {
        Mockito.doReturn(false).when((RemoteFileTreeItem) fileTreeItem).isSocketConnect();

        fileTreeItem.generateChildNodes();

        assertEquals(0, fileTreeItem.getChildren().size());
    }
}