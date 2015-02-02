package fr.epsi.widgets.explorer;

import fr.epsi.controller.MainController;
import fr.epsi.dto.FileDTO;
import fr.epsi.service.command.commands.RmCommand;
import javafx.scene.control.TreeItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static org.junit.Assert.assertFalse;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MainController.class)
public class RemoteExplorerTest extends AbstractExplorerTest {

    public RemoteExplorerTest() {
        super(Mockito.spy(new RemoteExplorer()));
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        rootNode = new TreeItem<String>("Server");
        explorer.setRoot(rootNode);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testInitializeNodesWithoutConnection() throws Exception {
        explorer.initializeNodes();

        assertFalse(explorer.getRoot().isExpanded());
        assertFalse(explorer.getRoot().getChildren().isEmpty());
    }

    @Test
    public void testOnDeleteDirectory() throws Exception {
        File fileToDelete= new File("test-directory-to-delete");
        FileDTO mockedFileDTO = Mockito.spy(new FileDTO(fileToDelete));

        RmCommand mockedRmCommand = Mockito.spy(new RmCommand(mockedFileDTO));
        Mockito.doReturn(false).doReturn(true).when(mockedRmCommand).isExecuted();

        Mockito.doReturn(mockedRmCommand).when((RemoteExplorer) explorer).createRmDirCommand(mockedFileDTO);

        explorer.doOnDeleteEvent(mockedFileDTO);

        Mockito.verify(mockedCommandQueue).offer(mockedRmCommand);
        Mockito.verify(mockedRmCommand, Mockito.atLeast(1)).isExecuted();
    }
}