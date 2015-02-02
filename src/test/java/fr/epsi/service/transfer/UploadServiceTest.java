package fr.epsi.service.transfer;

import fr.epsi.dto.FileDTO;
import fr.epsi.service.FTPServiceTest;
import fr.epsi.service.transfer.thread.UploadThread;
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
public class UploadServiceTest extends FTPServiceTest {
    private Queue<FileDTO> mockedUploadQueue;
    private UploadService mockedUploadService;
    private TransferQueue mockedTransferQueue;
    private ObservableList mockedTransferThreads;
    private UploadThread mockedUploadThread;

    public UploadServiceTest() {
        super(Mockito.spy(new UploadService()));

        mockedUploadService = (UploadService) mockedService;
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        mockedUploadService.setConsole(mockedConsole);

        mockedTransferThreads = Mockito.spy(FXCollections.observableArrayList());
        mockedTransferQueue = Mockito.mock(TransferQueue.class);
        Mockito.doReturn(mockedTransferThreads).when(mockedTransferQueue).getTransferThreads();
        mockedUploadService.setTransferQueue(mockedTransferQueue);

        Mockito.doReturn(mockedSocket).when(mockedUploadService).createSocket();

        mockedUploadQueue = Mockito.spy(new PriorityQueue<FileDTO>());
        mockedUploadService.setUploadQueue(mockedUploadQueue);
    }

    @Override
    public void tearDown() throws Exception {
        mockedSocket.close();

        super.tearDown();
    }

    @Test
    public void testOneUpload() throws Exception {
        FileDTO mockedFileDTO = Mockito.mock(FileDTO.class);

        File sourceFile = Mockito.spy(new File("/test-destination-file"));
        Mockito.doReturn(sourceFile).when(mockedFileDTO).getFile();

        File destinationFile = Mockito.spy(new File("/test-destination-file"));
        Mockito.doReturn(destinationFile).when(mockedFileDTO).getDestination();

        mockedUploadThread = Mockito.mock(UploadThread.class);
        Mockito.doReturn(mockedUploadThread).when(mockedUploadService).createUploadThread(mockedFileDTO);

        mockedUploadQueue.offer(mockedFileDTO);
        mockedUploadService.start();

        Thread.sleep(1000);

        Mockito.verify(mockedTransferThreads, Mockito.times(1)).add(mockedUploadThread);
        Mockito.verify(mockedUploadThread, Mockito.times(1)).start();
    }

    @Test
    public void testGetSocket() throws Exception {
        mockedUploadService.start();

        Socket socket = mockedUploadService.getSocket();
        assertNotNull(socket);

        assertEquals(socket, mockedUploadService.getSocket());
    }
}