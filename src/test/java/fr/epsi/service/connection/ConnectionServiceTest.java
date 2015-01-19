package fr.epsi.service.connection;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

@PrepareForTest(Platform.class)
@RunWith(PowerMockRunner.class)
public class ConnectionServiceTest {
    private TextArea mockedConsole;
    private Socket mockedSocket;
    private BufferedReader mockedBufferedReader;
    private PrintWriter mockedPrintWriter;
    private ConnectionService mockedConnectionService;

    @Before
    public void setUp() throws Exception {
        mockedConsole = Mockito.mock(TextArea.class);

        mockedBufferedReader = Mockito.mock(BufferedReader.class);
        Mockito.doReturn("Welcome").doReturn("auth : ok").when(mockedBufferedReader).readLine();

        mockedPrintWriter = Mockito.mock(PrintWriter.class);

        mockedSocket = Mockito.spy(new Socket());
        Mockito.doReturn(true).when(mockedSocket).isConnected();

        mockedConnectionService = Mockito.spy(new ConnectionService(mockedConsole));
        Mockito.doReturn(mockedSocket).when(mockedConnectionService).createSocket();
        Mockito.doReturn(mockedBufferedReader).when(mockedConnectionService).getSocketBufferedReader();
        Mockito.doReturn(mockedPrintWriter).when(mockedConnectionService).getSocketPrintWriter();

        PowerMockito.mockStatic(Platform.class);
        PowerMockito.when(Platform.isFxApplicationThread()).thenReturn(true);
    }

    @After
    public void tearDown() throws Exception {
        mockedConnectionService.cancel();
        while (!mockedConnectionService.getMessage().equals(ConnectionState.DISCONNECTED.toString())) {}

        Mockito.verify(mockedPrintWriter).println("exit");
        Mockito.verify(mockedPrintWriter, Mockito.times(2)).flush();

        Mockito.verify(mockedConsole).appendText("Disconnecting...");
        Mockito.verify(mockedSocket).close();
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