package fr.epsi.widgets.explorer.filetree.remote;

import fr.epsi.controller.MainController;
import fr.epsi.service.command.CommandService;
import fr.epsi.service.command.commands.FTPCommand;
import fr.epsi.service.command.commands.LsCommand;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.PriorityQueue;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MainController.class)
public class RemoteRootFileTreeItemTest {

    private RemoteRootFileTreeItem mockedRootTreeItem;
    private ObservableList<TreeItem> mockedChildren = FXCollections.observableArrayList();
    private LsCommand mockedLsCommand;
    private CommandService mockedCommandService;
    private Queue<FTPCommand> mockedCommandQueue = Mockito.spy(new PriorityQueue<FTPCommand>());
    private File mockedLsResponse;

    @Before
    public void setUp() throws Exception {
        mockedRootTreeItem = Mockito.spy(new RemoteRootFileTreeItem("test-value"));
        Mockito.doReturn(mockedChildren).when(mockedRootTreeItem).getChildren();

        mockedLsResponse = Mockito.spy(new File("test-folder"));
        Mockito.doReturn(true).when(mockedLsResponse).isDirectory();

        mockedLsCommand = Mockito.spy(new LsCommand());
        Mockito.doReturn(mockedLsCommand).when(mockedRootTreeItem).createLsCommand();
        Mockito.doReturn(mockedLsResponse).when(mockedLsCommand).getResponse();

        Mockito.doReturn(false).doReturn(true).when(mockedCommandQueue).contains(mockedLsCommand);

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
    public void testChildrenGeneration() throws Exception {
        mockedRootTreeItem.generateChildren();

        Mockito.verify(mockedCommandQueue, Mockito.atLeast(1)).contains(mockedLsCommand);
        Mockito.verify(mockedCommandQueue).offer(mockedLsCommand);

        assertEquals(1, mockedRootTreeItem.getChildren().size());
        assertEquals("test-folder", mockedRootTreeItem.getChildren().get(0).getValue());
        assertFalse(mockedRootTreeItem.isLeaf());
    }
}