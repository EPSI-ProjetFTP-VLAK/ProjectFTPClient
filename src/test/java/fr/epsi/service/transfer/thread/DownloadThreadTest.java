package fr.epsi.service.transfer.thread;

import fr.epsi.dto.FileDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;

public class DownloadThreadTest extends TransferThreadTest {

    private static long SOURCE_FILE_LENGTH = 10000L;

    private BufferedInputStream mockedBufferedInputStream;
    private FileOutputStream mockedFileOutputStream;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        Mockito.doReturn(SOURCE_FILE_LENGTH).when(mockedSourceFile).length();
        mockedFileDTO = Mockito.spy(new FileDTO(mockedSourceFile));
        Mockito.doReturn(mockedDestinationFile).when(mockedFileDTO).getDestination();

        mockedBufferedInputStream = PowerMockito.mock(BufferedInputStream.class);
        mockedFileOutputStream = Mockito.mock(FileOutputStream.class);

        mockedTransferThread = Mockito.spy(new DownloadThread(mockedFileDTO, mockedSocket));
        Mockito.doReturn(mockedPrintWriter).when(mockedTransferThread).getSocketPrintWriter();

        Mockito.doReturn(mockedBufferedInputStream).when((DownloadThread) mockedTransferThread).getSocketBufferedInputStream();
        Mockito.doReturn(mockedFileOutputStream).when((DownloadThread) mockedTransferThread).getFileOutputStream();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testDownload() throws Exception {
        byte[] destinationBuffer = new byte[4096];

        Mockito.when(mockedBufferedInputStream.read(destinationBuffer)).thenReturn((int) SOURCE_FILE_LENGTH).thenReturn(-1);

        mockedTransferThread.run();
        mockedTransferThread.join();

        Mockito.verify(mockedFileOutputStream, Mockito.times(1)).write(destinationBuffer, 0, (int) SOURCE_FILE_LENGTH);
    }

    @Test
    public void testInterrupt() throws Exception {
        byte[] destinationBuffer = new byte[4096];

        Mockito.when(mockedBufferedInputStream.read(destinationBuffer)).thenReturn((int) SOURCE_FILE_LENGTH).thenReturn(-1);

        mockedTransferThread.run();
        mockedTransferThread.interrupt();

        Mockito.verify(mockedDestinationFile, Mockito.times(1)).delete();
    }
}