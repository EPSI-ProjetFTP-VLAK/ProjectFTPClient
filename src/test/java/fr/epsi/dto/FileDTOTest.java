package fr.epsi.dto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileDTOTest {

    private FileDTO fileDTO;
    private File folder;

    @Before
    public void setUp() throws Exception {
        // Main folder
        folder = Mockito.spy(new File("test-folder"));
        Mockito.doReturn(true).when(folder).isDirectory();

        // Subfolder
        File subFolder = Mockito.spy(new File("test-sub-folder"));
        Mockito.doReturn(true).when(subFolder).isDirectory();

        // Empty Subfolder
        File emptySubFolder = Mockito.spy(new File("test-empty-sub-folder"));
        Mockito.doReturn(true).when(emptySubFolder).isDirectory();

        // Subfolder files
        File[] subFolderFiles = new File[] { new File("test-sub-file-1"), new File("test-sub-file-2") };
        Mockito.doReturn(subFolderFiles).when(subFolder).listFiles();

        // Folder files
        File[] files = new File[] { new File("test-file-1"), subFolder, emptySubFolder };
        Mockito.doReturn(files).when(folder).listFiles();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testFileToDTO() throws Exception {
        fileDTO = new FileDTO(folder, true);

        assertEquals(folder.getName(), fileDTO.getName());
        assertEquals(folder.isDirectory(), fileDTO.isDirectory());

        assertEquals(3, fileDTO.getChildren().size());

        assertFalse(folder.listFiles()[0].isDirectory());
        assertEquals(folder.listFiles()[0].getName(), fileDTO.getChildren().get(0).getName());

        assertTrue(folder.listFiles()[1].isDirectory());
        assertEquals(folder.listFiles()[1].getName(), fileDTO.getChildren().get(1).getName());
        assertEquals(0, fileDTO.getChildren().get(1).getChildren().size());

        assertTrue(folder.listFiles()[2].isDirectory());
        assertEquals(folder.listFiles()[2].getName(), fileDTO.getChildren().get(2).getName());
        assertEquals(0, fileDTO.getChildren().get(2).getChildren().size());
    }
}