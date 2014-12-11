package fr.epsi.controller.explorer.local;

import javafx.scene.control.TreeItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FileSystemView.class)
public class LocalExplorerControllerTest {

    public static final String HOSTNAME = "test-computer";

    private File[] testRoots;
    private LocalExplorerController localExplorerController;

    @Before
    public void setUp() throws Exception {
        localExplorerController = new LocalExplorerController();
        localExplorerController = Mockito.spy(localExplorerController);

        Mockito.when(localExplorerController.getHostName()).thenReturn(HOSTNAME);

        testRoots = new File[2];
        testRoots[0] = new File("test-file-1");
        testRoots[1] = new File("test-file-2");

        FileSystemView mockedFileSystemView = Mockito.mock(FileSystemView.class);
        Mockito.when(mockedFileSystemView.getRoots()).thenReturn(testRoots);

        PowerMockito.mockStatic(FileSystemView.class);
        PowerMockito.when(FileSystemView.getFileSystemView()).thenReturn(mockedFileSystemView);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGenerateRootNode() throws Exception {
        TreeItem<String> rootNode = localExplorerController.generateRootNode();

        assertEquals(HOSTNAME, rootNode.getValue());
    }

    @Test
    public void testGenerateChildNodes() throws Exception {
        TreeItem<String> rootNode = new TreeItem<String>(HOSTNAME);

        localExplorerController.generateChildNodes(rootNode);

        assertEquals(HOSTNAME, rootNode.getValue());
        assertEquals(testRoots[0].getName(), rootNode.getChildren().get(0).getValue());
        assertEquals(testRoots[1].getName(), rootNode.getChildren().get(1).getValue());
    }
}