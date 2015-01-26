package fr.epsi.service.transfer;

import fr.epsi.dto.FileDTO;
import fr.epsi.dto.TransferDTO;
import fr.epsi.service.FTPServiceTest;
import fr.epsi.widgets.transfer.TransferQueue;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;
import java.util.PriorityQueue;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@PrepareForTest(Platform.class)
@RunWith(PowerMockRunner.class)
public class DownloadServiceTest extends FTPServiceTest {
    private Queue<FileDTO> downloadQueue;
    private DownloadService mockedDownloadService;
    private TransferQueue mockedTransferQueue;
    private ObservableList mockedTransferDTOs;
    private TransferDTO mockedTransferDTO;
    private BufferedInputStream mockedBufferedInputStream;
    private FileOutputStream mockedFileOutputStream;
    private FileDTO fileToDownload;

    public DownloadServiceTest() {
        super(Mockito.spy(new DownloadService()));

        mockedDownloadService = (DownloadService) mockedService;
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        mockedDownloadService.setConsole(mockedConsole);

        mockedTransferDTOs = Mockito.spy(FXCollections.observableArrayList());
        mockedTransferQueue = Mockito.mock(TransferQueue.class);
        Mockito.doReturn(mockedTransferDTOs).when(mockedTransferQueue).getTransferDTOs();
        mockedDownloadService.setTransferQueue(mockedTransferQueue);

        Mockito.doReturn(mockedSocket).when(mockedDownloadService).createSocket();

        downloadQueue = Mockito.spy(new PriorityQueue<FileDTO>());
        mockedDownloadService.setDownloadQueue(downloadQueue);

        mockedBufferedInputStream = Mockito.mock(BufferedInputStream.class);
        Mockito.doReturn(mockedBufferedInputStream).when(mockedDownloadService).getSocketBufferedInputStream();

        mockedFileOutputStream = Mockito.mock(FileOutputStream.class);
    }

    @Test
    public void testOneDownload() throws Exception {
        byte[] destinationBuffer = new byte[1024];
        long sourceFileLength = 10000L;

        fileToDownload = Mockito.mock(FileDTO.class);

        File sourceFile = Mockito.spy(new File("/test-destination-file"));
        Mockito.doReturn(sourceFileLength).when(sourceFile).length();
        Mockito.doReturn(sourceFile).when(fileToDownload).getFile();

        File destinationFile = Mockito.spy(new File("/test-destination-file"));
        Mockito.doReturn(destinationFile).when(fileToDownload).getDestination();

        Mockito.when(mockedBufferedInputStream.read(destinationBuffer, 0, 1024)).thenReturn((int) sourceFileLength).thenReturn(-1);

        mockedTransferDTO = Mockito.spy(new TransferDTO(fileToDownload));
        Mockito.doReturn(mockedTransferDTO).when(mockedDownloadService).createTransferDTO(fileToDownload);

        Mockito.doReturn(mockedFileOutputStream).when(mockedDownloadService).getFileOutputStream(fileToDownload);

        mockedDownloadService.getDownloadQueue().offer(fileToDownload);
        mockedDownloadService.setSocket(mockedSocket);

        mockedDownloadService.start();

        while (mockedTransferDTO.getProgress() < 1) {}

        Mockito.verify(mockedTransferDTOs, Mockito.times(1)).add(mockedTransferDTO);

        Mockito.verify(mockedTransferDTO, Mockito.atLeast(1)).setProgress(Mockito.anyDouble());

        Mockito.verify(mockedFileOutputStream, Mockito.times(1)).write(destinationBuffer, 0, (int) sourceFileLength);
    }

    @Test
    public void testGetSocket() throws Exception {
        mockedDownloadService.start();

        Socket socket = mockedDownloadService.getSocket();
        assertNotNull(socket);

        assertEquals(socket, mockedDownloadService.getSocket());
    }
}