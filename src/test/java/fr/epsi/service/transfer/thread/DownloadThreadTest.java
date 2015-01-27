package fr.epsi.service.transfer.thread;

import fr.epsi.dto.FileDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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

        mockedBufferedInputStream = Mockito.mock(BufferedInputStream.class);
        mockedFileOutputStream = Mockito.mock(FileOutputStream.class);

        transferThread = new DownloadThread(mockedFileDTO, mockedBufferedInputStream, mockedFileOutputStream);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testDownload() throws Exception {
        byte[] destinationBuffer = new byte[1024];

        Mockito.when(mockedBufferedInputStream.read(destinationBuffer, 0, 1024)).thenReturn((int) SOURCE_FILE_LENGTH).thenReturn(-1);

        transferThread.run();
        transferThread.join();

        Mockito.verify(mockedFileOutputStream, Mockito.times(1)).write(destinationBuffer, 0, (int) SOURCE_FILE_LENGTH);
    }

    @Test
    public void testInterrupt() throws Exception {
        byte[] destinationBuffer = new byte[1024];

        Mockito.when(mockedBufferedInputStream.read(destinationBuffer, 0, 1024)).thenReturn((int) SOURCE_FILE_LENGTH).thenReturn(-1);

        transferThread.run();
        transferThread.interrupt();

        Mockito.verify(mockedDestinationFile, Mockito.times(1)).delete();
    }
}