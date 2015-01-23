package fr.epsi.service.transfer;

import fr.epsi.dto.FileDTO;
import fr.epsi.service.FTPServiceTest;
import javafx.application.Platform;
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
        byte[] dataBytes = "test".getBytes();
        int dataBytesLength = dataBytes.length;
        Mockito.when(mockedBufferedInputStream.read(destinationBuffer, 0, 1024)).thenReturn(dataBytesLength).thenReturn(-1);

        fileToDownload = Mockito.mock(FileDTO.class);
        File destinationFile = Mockito.spy(new File("/test-destination-file"));
        Mockito.doReturn(destinationFile).when(fileToDownload).getDestination();

        Mockito.doReturn(mockedFileOutputStream).when(mockedDownloadService).getFileOutputStream(fileToDownload);

        mockedDownloadService.getDownloadQueue().offer(fileToDownload);

        mockedDownloadService.start();

        while (downloadQueue.size() > 0) {}

        Mockito.verify(mockedFileOutputStream, Mockito.times(1)).write(destinationBuffer, 0, dataBytesLength);
    }

    @Test
    public void testGetSocket() throws Exception {
        mockedDownloadService.start();

        Socket socket = mockedDownloadService.getSocket();
        assertNotNull(socket);

        assertEquals(socket, mockedDownloadService.getSocket());
    }
}