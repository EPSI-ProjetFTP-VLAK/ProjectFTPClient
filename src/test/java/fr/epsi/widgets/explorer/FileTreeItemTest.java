package fr.epsi.widgets.explorer;

import fr.epsi.widgets.explorer.filetree.FileTreeItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class FileTreeItemTest {

    private Class<? extends FileTreeItem> fileTreeItemClass;
    protected FileTreeItem fileTreeItem;
    protected File file;
    protected TreeItem<String> rootNode;
    protected File[] testRoots;
    protected ObservableList<TreeItem> mockedChildren = FXCollections.observableArrayList();

    public FileTreeItemTest() {
        fileTreeItemClass = FileTreeItem.class;
    }

    public FileTreeItemTest(Class<? extends FileTreeItem> fileTreeItemClass) {
        file = Mockito.spy(new File("test-file"));

        this.fileTreeItemClass = fileTreeItemClass;
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testFile() throws Exception {
        file = Mockito.spy(new File("test-file"));
        Mockito.doReturn(false).when(file).isDirectory();

        createFileTreeItem();

        assertEquals("test-file", fileTreeItem.getValue());
        assertTrue(fileTreeItem.getChildren().isEmpty());
    }

    @Test
    public void testFolder() throws Exception {
        file = Mockito.spy(new File("test-folder"));
        File[] files = new File[] { new File("test-file-1"), new File("test-file-2") };

        Mockito.doReturn(files).when(file).listFiles();
        Mockito.doReturn(true).when(file).isDirectory();

        createFileTreeItem();

        assertEquals("test-folder", fileTreeItem.getValue());
        assertFalse(fileTreeItem.isExpanded());
        assertEquals(0, fileTreeItem.getChildren().size());
    }

    private void createFileTreeItem()
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (fileTreeItem == null) {
            fileTreeItem = new FileTreeItem(file) {
                @Override
                public void generateChildNodes() {

                }
            };
        } else {
            fileTreeItem = Mockito.spy(fileTreeItemClass.getDeclaredConstructor(File.class).newInstance(file));
        }
    }
}