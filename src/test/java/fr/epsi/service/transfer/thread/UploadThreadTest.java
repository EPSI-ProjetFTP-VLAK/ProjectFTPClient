package fr.epsi.service.transfer.thread;

import fr.epsi.dto.FileDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class UploadThreadTest extends TransferThreadTest {

    private static long SOURCE_FILE_LENGTH = 10000L;

    private Socket mockedSocket;
    private PrintWriter mockedPrintWriter;
    private DataOutputStream mockedDataOutputStream;
    private FileInputStream mockedFileInputStream;
    private BufferedReader mockedBufferedReader;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        Mockito.doReturn(SOURCE_FILE_LENGTH).when(mockedSourceFile).length();
        mockedFileDTO = Mockito.spy(new FileDTO(mockedSourceFile));
        Mockito.doReturn(mockedDestinationFile).when(mockedFileDTO).getDestination();

        mockedPrintWriter = Mockito.mock(PrintWriter.class);
        mockedDataOutputStream = Mockito.mock(DataOutputStream.class);
        mockedFileInputStream = Mockito.mock(FileInputStream.class);

        mockedBufferedReader = Mockito.mock(BufferedReader.class);
        Mockito.doReturn("upload : OK").when(mockedBufferedReader).readLine();

        mockedSocket = Mockito.mock(Socket.class);

        transferThread = Mockito.spy(new UploadThread(mockedFileDTO, mockedSocket));

        Mockito.doReturn(mockedPrintWriter).when((UploadThread) transferThread).getSocketPrintWriter();
        Mockito.doReturn(mockedDataOutputStream).when((UploadThread) transferThread).getSocketDataOutputStream();
        Mockito.doReturn(mockedFileInputStream).when((UploadThread) transferThread).getSocketFileInputStream();
        Mockito.doReturn(mockedBufferedReader).when((UploadThread) transferThread).getSocketBufferedReader();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testUpload() throws Exception {
        byte[] destinationBuffer = new byte[4096];

        Mockito.when(mockedFileInputStream.read(destinationBuffer)).thenReturn((int) SOURCE_FILE_LENGTH).thenReturn(-1);

        transferThread.run();
        transferThread.join();

        Mockito.verify(mockedDataOutputStream, Mockito.times(1)).write(destinationBuffer, 0, (int) SOURCE_FILE_LENGTH);
    }

    @Test
    public void testInterrupt() throws Exception {
        byte[] destinationBuffer = new byte[4096];

        Mockito.when(mockedFileInputStream.read(destinationBuffer)).thenReturn((int) SOURCE_FILE_LENGTH).thenReturn(-1);

        transferThread.run();
        transferThread.interrupt();
    }
}