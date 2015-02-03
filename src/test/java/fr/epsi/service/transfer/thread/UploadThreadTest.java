package fr.epsi.service.transfer.thread;

import fr.epsi.dto.FileDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.net.Socket;

public class UploadThreadTest extends TransferThreadTest {

    private static long SOURCE_FILE_LENGTH = 10000L;

    private Socket mockedSocket;
    private BufferedOutputStream mockedBufferedOutputStream;
    private FileInputStream mockedFileInputStream;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        Mockito.doReturn(SOURCE_FILE_LENGTH).when(mockedSourceFile).length();
        mockedFileDTO = Mockito.spy(new FileDTO(mockedSourceFile));
        Mockito.doReturn(mockedDestinationFile).when(mockedFileDTO).getDestination();

        mockedBufferedOutputStream = Mockito.mock(BufferedOutputStream.class);
        mockedFileInputStream = Mockito.mock(FileInputStream.class);

        mockedSocket = Mockito.mock(Socket.class);

        transferThread = new UploadThread(mockedFileDTO, mockedSocket, mockedBufferedOutputStream, mockedFileInputStream);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testUpload() throws Exception {
        byte[] destinationBuffer = new byte[1024];

        Mockito.when(mockedFileInputStream.read(destinationBuffer)).thenReturn((int) SOURCE_FILE_LENGTH).thenReturn(-1);

        transferThread.run();
        transferThread.join();

        Mockito.verify(mockedBufferedOutputStream, Mockito.times(1)).write(destinationBuffer, 0, (int) SOURCE_FILE_LENGTH);
    }

    @Test
    public void testInterrupt() throws Exception {
        byte[] destinationBuffer = new byte[1024];

        Mockito.when(mockedFileInputStream.read(destinationBuffer)).thenReturn((int) SOURCE_FILE_LENGTH).thenReturn(-1);

        transferThread.run();
        transferThread.interrupt();
    }
}