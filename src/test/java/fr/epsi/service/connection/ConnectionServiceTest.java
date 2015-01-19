package fr.epsi.service.connection;

import fr.epsi.service.FTPServiceTest;
import javafx.application.Platform;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

@PrepareForTest(Platform.class)
@RunWith(PowerMockRunner.class)
public class ConnectionServiceTest extends FTPServiceTest {
    private ConnectionService mockedConnectionService;

    public ConnectionServiceTest() {
        super(Mockito.spy(new ConnectionService()));

        mockedConnectionService = (ConnectionService) mockedService;
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        mockedConnectionService.setConsole(mockedConsole);

        Mockito.doReturn(mockedSocket).when(mockedConnectionService).createSocket();
        Mockito.doReturn("Welcome").doReturn("AUTH : OK").when(mockedBufferedReader).readLine();
    }

    @Test
    public void testSuccessfulConnection() throws Exception {
        mockedConnectionService.setHost("127.0.0.1");
        mockedConnectionService.setUsername("user");
        mockedConnectionService.setPassword("password");
        mockedConnectionService.setPort(4000);

        mockedConnectionService.start();

        while (!mockedConnectionService.getMessage().equals(ConnectionState.CONNECTED.toString())) {}
        while (!mockedConnectionService.getMessage().equals(ConnectionState.AUTHENTICATED.toString())) {}

        try {
            Mockito.verify(mockedBufferedReader, Mockito.times(2)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Mockito.verify(mockedConsole).appendText("Initializing connection...\n");
        Mockito.verify(mockedConsole).appendText("Connection successful !\n");
        Mockito.verify(mockedConsole).appendText("Welcome\n");
        Mockito.verify(mockedConsole, Mockito.never()).appendText("Failed to connect to server !\n");

        Mockito.verify(mockedPrintWriter).println("user password");
        Mockito.verify(mockedPrintWriter).flush();
    }
}