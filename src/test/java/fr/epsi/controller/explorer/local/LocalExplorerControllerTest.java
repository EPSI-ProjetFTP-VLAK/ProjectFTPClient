package fr.epsi.controller.explorer.local;

import javafx.scene.control.TreeItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.swing.filechooser.FileSystemView;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FileSystemView.class)
public class LocalExplorerControllerTest {

    public static final String HOSTNAME = "test-computer";

    private LocalExplorerController localExplorerController;

    @Before
    public void setUp() throws Exception {
        localExplorerController = new LocalExplorerController();
        localExplorerController = Mockito.spy(localExplorerController);

        Mockito.when(localExplorerController.getHostName()).thenReturn(HOSTNAME);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGenerateRootNode() throws Exception {
        TreeItem<String> rootNode = localExplorerController.generateRootNode();

        assertEquals(HOSTNAME, rootNode.getValue());
    }
}