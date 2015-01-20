package fr.epsi.widgets.explorer;

import fr.epsi.widgets.explorer.filetree.FileTreeItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileTreeItemTest {

    protected FileTreeItem fileTreeItem;
    private File file;

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

        fileTreeItem = new FileTreeItem(file) {
            @Override
            public void generateChildNodes() {

            }
        };

        assertEquals("test-file", fileTreeItem.getValue());
        assertTrue(fileTreeItem.getChildren().isEmpty());
    }

    @Test
    public void testFolder() throws Exception {
        file = Mockito.spy(new File("test-folder"));
        File[] files = new File[] { new File("test-file-1"), new File("test-file-2") };

        Mockito.doReturn(files).when(file).listFiles();
        Mockito.doReturn(true).when(file).isDirectory();

        fileTreeItem = new FileTreeItem(file) {
            @Override
            public void generateChildNodes() {

            }
        };

        assertEquals("test-folder", fileTreeItem.getValue());
        assertFalse(fileTreeItem.isExpanded());
        assertFalse(fileTreeItem.getChildren().isEmpty());
    }
}