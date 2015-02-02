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

import java.io.BufferedInputStream;
import java.io.FileOutputStream;

@RunWith(PowerMockRunner.class)
@PrepareForTest(IOUtils.class)
public class DownloadThreadTest extends TransferThreadTest {

    private BufferedInputStream mockedBufferedInputStream;
    private FileOutputStream mockedFileOutputStream;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        mockedFileDTO = Mockito.spy(new FileDTO(mockedSourceFile));
        Mockito.doReturn(mockedDestinationFile).when(mockedFileDTO).getDestination();

        mockedBufferedInputStream = Mockito.mock(BufferedInputStream.class);
        mockedFileOutputStream = Mockito.mock(FileOutputStream.class);

        transferThread = new DownloadThread(mockedFileDTO, mockedBufferedInputStream, mockedFileOutputStream);

        PowerMockito.mockStatic(IOUtils.class);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testDownload() throws Exception {
        transferThread.run();
        transferThread.join();

        PowerMockito.verifyStatic();
        IOUtils.copy(mockedBufferedInputStream, mockedFileOutputStream);
    }

    @Test
    public void testInterrupt() throws Exception {
        transferThread.run();
        transferThread.interrupt();

        Mockito.verify(mockedDestinationFile, Mockito.times(1)).delete();
    }
}