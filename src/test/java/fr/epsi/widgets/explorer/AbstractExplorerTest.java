package fr.epsi.widgets.explorer;

import fr.epsi.controller.MainController;
import fr.epsi.dto.FileDTO;
import fr.epsi.service.command.CommandService;
import fr.epsi.service.command.commands.FTPCommand;
import fr.epsi.widgets.explorer.filetree.FileTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.swing.filechooser.FileSystemView;
import java.util.PriorityQueue;
import java.util.Queue;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FileSystemView.class, MainController.class})
public class AbstractExplorerTest {;

    protected AbstractExplorer explorer;
    protected TreeItem<String> rootNode;
    protected CommandService mockedCommandService;
    protected Queue<FTPCommand> mockedCommandQueue;

    public AbstractExplorerTest() {
        explorer = new AbstractExplorer() {
            @Override
            public void doOnFileDrag(MouseEvent mouseEvent) {

            }

            @Override
            public void doOnFileDrop(FileTreeItem sourceFileTreeItem, FileTreeItem targetFileTreeItem, DragEvent dragEvent) {

            }

            @Override
            public void doOnDeleteEvent(FileDTO fileDTO) {

            }

            @Override
            public void initializeNodes() {

            }
        };
    }

    public AbstractExplorerTest(AbstractExplorer explorer) {
        this.explorer = explorer;
    }

    @Before
    public void setUp() throws Exception {
        mockedCommandQueue = Mockito.spy(new PriorityQueue<FTPCommand>());
        mockedCommandService = Mockito.mock(CommandService.class);
        Mockito.doReturn(mockedCommandQueue).when(mockedCommandService).getCommandQueue();

        PowerMockito.mockStatic(MainController.class);
        PowerMockito.when(MainController.getCommandService()).thenReturn(mockedCommandService);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testEmpty() throws Exception {

    }
}