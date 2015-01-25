package fr.epsi.widgets.explorer;

import fr.epsi.controller.MainController;
import fr.epsi.dto.FileDTO;
import fr.epsi.service.command.commands.DownloadCommand;
import fr.epsi.widgets.explorer.filetree.FileTreeItem;
import fr.epsi.widgets.explorer.filetree.local.LocalFileTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.input.DragEvent;
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
@PrepareForTest({FileSystemView.class, MainController.class})
public class LocalExplorerTest extends AbstractExplorerTest {

    public static final String HOSTNAME = "test-computer";

    private File[] testRoots;

    public LocalExplorerTest() {
        super(Mockito.spy(new LocalExplorer()));
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

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
        explorer.setRoot(rootNode);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testInitializeNodes() throws Exception {
        explorer.initializeNodes();

        assertEquals(HOSTNAME, rootNode.getValue());
        assertEquals(testRoots[0].getName(), rootNode.getChildren().get(0).getValue());
        assertEquals(testRoots[1].getName(), rootNode.getChildren().get(1).getValue());
    }

    @Test
    public void testDoOnFileDrop() throws Exception {
        assertEquals(0, mockedCommandQueue.size());

        FileDTO mockedSourceFileDTO = Mockito.spy(new FileDTO(new File("test-remote-file")));
        FileTreeItem mockedSourceFileTreeItem = Mockito.spy(new LocalFileTreeItem(mockedSourceFileDTO));

        File mockedTargetFile = Mockito.spy(new File("test-local-directory"));
        FileDTO mockedTargetFileDTO = Mockito.spy(new FileDTO(mockedTargetFile));
        FileTreeItem mockedTargetFileTreeItem = Mockito.spy(new LocalFileTreeItem(mockedTargetFileDTO));

        DownloadCommand mockedDownloadCommand = Mockito.spy(new DownloadCommand(mockedSourceFileDTO));
        Mockito.doReturn(mockedDownloadCommand).when((LocalExplorer) explorer).createDownloadCommand(mockedSourceFileDTO);

        explorer.doOnFileDrop(mockedSourceFileTreeItem, mockedTargetFileTreeItem, Mockito.mock(DragEvent.class));

        assertEquals(mockedTargetFile.getName() + System.getProperty("file.separator") + mockedSourceFileDTO.getName(), mockedSourceFileDTO.getDestination().toPath().toString());

        Mockito.verify(mockedCommandQueue, Mockito.times(1)).offer(mockedDownloadCommand);
        assertEquals(1, mockedCommandQueue.size());
    }
}