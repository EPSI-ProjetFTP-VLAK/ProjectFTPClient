package fr.epsi.widgets.explorer.filetree.local;

import fr.epsi.widgets.explorer.FileTreeItemTest;
import javafx.scene.control.TreeItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LocalFileTreeItemTest extends FileTreeItemTest {

    public static final String HOSTNAME = "test-computer";

    public LocalFileTreeItemTest()
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        super(LocalFileTreeItem.class);
    }

    @Before
    public void setUp() throws Exception {
        testRoots = new File[1];

        File firstDirectoryMock = Mockito.spy(new File("test-directory-1"));
        Mockito.doReturn(true).when(firstDirectoryMock).isDirectory();
        Mockito.doReturn(new File[] { new File("test-file-1"), new File("test-file-2") }).when(firstDirectoryMock).listFiles();
        testRoots[0] = firstDirectoryMock;

        fileTreeItem = Mockito.spy(new LocalFileTreeItem(testRoots[0]));
        Mockito.doReturn(mockedChildren).when(fileTreeItem).getChildren();

        rootNode = new TreeItem<String>(HOSTNAME);
        rootNode.getChildren().add(fileTreeItem);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGenerateChildNodes() throws Exception {
        fileTreeItem.generateChildNodes();
        assertEquals(testRoots[0].listFiles()[0].getName(), fileTreeItem.getChildren().get(0).getValue());
        assertTrue(fileTreeItem.getChildren().get(0).isLeaf());

        assertEquals(testRoots[0].listFiles()[1].getName(), fileTreeItem.getChildren().get(1).getValue());
        assertTrue(fileTreeItem.getChildren().get(1).isLeaf());
    }
}