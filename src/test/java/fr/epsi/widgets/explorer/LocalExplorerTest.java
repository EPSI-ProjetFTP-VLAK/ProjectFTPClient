package fr.epsi.widgets.explorer;

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
public class LocalExplorerTest {

    public static final String HOSTNAME = "test-computer";

    private File[] testRoots;
    private LocalExplorer localExplorer;
    private TreeItem<String> rootNode;

    @Before
    public void setUp() throws Exception {
        localExplorer = new LocalExplorer();

        testRoots = new File[2];

        File firstDirectoryMock = Mockito.spy(new File("test-directory-1"));
        Mockito.doReturn(true).when(firstDirectoryMock).isDirectory();
        Mockito.doReturn(new File[] { new File("test-file-1"), new File("test-file-2") }).when(firstDirectoryMock).listFiles();
        testRoots[0] = firstDirectoryMock;

        File secondDirectoryMock = Mockito.spy(new File("test-directory-2"));
        Mockito.doReturn(true).when(secondDirectoryMock).isDirectory();
        testRoots[1] = secondDirectoryMock;

        FileSystemView mockedFileSystemView = Mockito.mock(FileSystemView.class);
        Mockito.when(mockedFileSystemView.getRoots()).thenReturn(testRoots);

        PowerMockito.mockStatic(FileSystemView.class);
        PowerMockito.when(FileSystemView.getFileSystemView()).thenReturn(mockedFileSystemView);

        rootNode = new TreeItem<String>(HOSTNAME);
        localExplorer.setRoot(rootNode);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGenerateChildNodes() throws Exception {
        localExplorer.generateChildNodes();

        assertEquals(HOSTNAME, rootNode.getValue());
        assertEquals(testRoots[0].getName(), rootNode.getChildren().get(0).getValue());
        assertEquals(testRoots[1].getName(), rootNode.getChildren().get(1).getValue());
    }
}