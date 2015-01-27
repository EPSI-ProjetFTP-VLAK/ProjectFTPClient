package fr.epsi.service.transfer;

import fr.epsi.dto.FileDTO;
import fr.epsi.service.FTPServiceTest;
import fr.epsi.service.transfer.thread.DownloadThread;
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

import java.io.File;
import java.net.Socket;
import java.util.PriorityQueue;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@PrepareForTest(Platform.class)
@RunWith(PowerMockRunner.class)
public class DownloadServiceTest extends FTPServiceTest {
    private Queue<FileDTO> mockedDownloadQueue;
    private DownloadService mockedDownloadService;
    private TransferQueue mockedTransferQueue;
    private ObservableList mockedTransferThreads;
    private DownloadThread mockedDownloadThread;

    public DownloadServiceTest() {
        super(Mockito.spy(new DownloadService()));

        mockedDownloadService = (DownloadService) mockedService;
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        mockedDownloadService.setConsole(mockedConsole);

        mockedTransferThreads = Mockito.spy(FXCollections.observableArrayList());
        mockedTransferQueue = Mockito.mock(TransferQueue.class);
        Mockito.doReturn(mockedTransferThreads).when(mockedTransferQueue).getTransferThreads();
        mockedDownloadService.setTransferQueue(mockedTransferQueue);

        Mockito.doReturn(mockedSocket).when(mockedDownloadService).createSocket();

        mockedDownloadQueue = Mockito.spy(new PriorityQueue<FileDTO>());
        mockedDownloadService.setDownloadQueue(mockedDownloadQueue);
    }

    @Override
    public void tearDown() throws Exception {
        mockedSocket.close();

        super.tearDown();
    }

    @Test
    public void testOneDownload() throws Exception {
        FileDTO mockedFileDTO = Mockito.mock(FileDTO.class);

        File sourceFile = Mockito.spy(new File("/test-destination-file"));
        Mockito.doReturn(sourceFile).when(mockedFileDTO).getFile();

        File destinationFile = Mockito.spy(new File("/test-destination-file"));
        Mockito.doReturn(destinationFile).when(mockedFileDTO).getDestination();

        mockedDownloadThread = Mockito.mock(DownloadThread.class);
        Mockito.doReturn(mockedDownloadThread).when(mockedDownloadService).createDownloadThread(mockedFileDTO);

        mockedDownloadQueue.offer(mockedFileDTO);
        mockedDownloadService.start();

        Thread.sleep(1000);

        Mockito.verify(mockedTransferThreads, Mockito.times(1)).add(mockedDownloadThread);
        Mockito.verify(mockedDownloadThread, Mockito.times(1)).start();
    }

    @Test
    public void testGetSocket() throws Exception {
        mockedDownloadService.start();

        Socket socket = mockedDownloadService.getSocket();
        assertNotNull(socket);

        assertEquals(socket, mockedDownloadService.getSocket());
    }
}