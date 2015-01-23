package fr.epsi.service.transfer;

import fr.epsi.service.FTPServiceTest;
import javafx.application.Platform;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@PrepareForTest(Platform.class)
@RunWith(PowerMockRunner.class)
public class DownloadServiceTest extends FTPServiceTest {
    private DownloadService mockedDownloadService;

    public DownloadServiceTest() {
        super(Mockito.spy(new DownloadService()));

        mockedDownloadService = (DownloadService) mockedService;
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        mockedDownloadService.setConsole(mockedConsole);

        Mockito.doReturn(mockedSocket).when(mockedDownloadService).createSocket();
    }

    @Test
    public void testGetSocket() throws Exception {
        mockedDownloadService.start();

        Socket socket = mockedDownloadService.getSocket();
        assertNotNull(socket);

        assertEquals(socket, mockedDownloadService.getSocket());
    }
}