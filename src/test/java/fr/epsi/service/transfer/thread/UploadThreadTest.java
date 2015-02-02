package fr.epsi.service.transfer.thread;

import fr.epsi.dto.FileDTO;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.net.Socket;

@RunWith(PowerMockRunner.class)
@PrepareForTest(IOUtils.class)
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

        PowerMockito.mockStatic(IOUtils.class);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testUpload() throws Exception {
        transferThread.run();
        transferThread.join();

        PowerMockito.verifyStatic();
        IOUtils.copy(mockedFileInputStream, mockedBufferedOutputStream);
    }

    @Test
    public void testInterrupt() throws Exception {
        transferThread.run();
        transferThread.interrupt();
    }
}