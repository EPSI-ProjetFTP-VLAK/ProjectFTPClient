package fr.epsi.widgets.explorer.filetree.local;

import fr.epsi.widgets.explorer.FileTreeItemTest;
import fr.epsi.widgets.explorer.filetree.FileTreeItem;
import javafx.scene.control.TreeItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LocalFileTreeItemTest extends FileTreeItemTest {

    public static final String HOSTNAME = "test-computer";

    private File[] testRoots;
    private TreeItem<String> rootNode;

    @Before
    public void setUp() throws Exception {
        testRoots = new File[2];

        File firstDirectoryMock = Mockito.spy(new File("test-directory-1"));
        Mockito.doReturn(true).when(firstDirectoryMock).isDirectory();
        Mockito.doReturn(new File[] { new File("test-file-1"), new File("test-file-2") }).when(firstDirectoryMock).listFiles();
        testRoots[0] = firstDirectoryMock;

        File secondDirectoryMock = Mockito.spy(new File("test-directory-2"));
        Mockito.doReturn(true).when(secondDirectoryMock).isDirectory();
        testRoots[1] = secondDirectoryMock;

        rootNode = new TreeItem<String>(HOSTNAME);
        rootNode.getChildren().add(new LocalFileTreeItem(testRoots[0]));
        rootNode.getChildren().add(new LocalFileTreeItem(testRoots[1]));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testPrepareChildNodes() throws Exception {
        ((FileTreeItem) rootNode.getChildren().get(0)).generateChildNodes();
        assertEquals(testRoots[0].listFiles()[0].getName(), rootNode.getChildren().get(0).getChildren().get(0).getValue());
        assertTrue(rootNode.getChildren().get(0).getChildren().get(0).isLeaf());

        assertEquals(testRoots[0].listFiles()[1].getName(), rootNode.getChildren().get(0).getChildren().get(1).getValue());
        assertTrue(rootNode.getChildren().get(0).getChildren().get(1).isLeaf());

        ((FileTreeItem) rootNode.getChildren().get(1)).generateChildNodes();
        assertEquals(0, rootNode.getChildren().get(1).getChildren().size());
        assertTrue(rootNode.getChildren().get(1).isLeaf());
    }
}