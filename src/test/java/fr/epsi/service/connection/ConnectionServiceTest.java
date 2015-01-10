package fr.epsi.service.connection;

import javafx.application.Platform;
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
    private Socket mockedSocket;
    private BufferedReader mockedBufferedReader;
    private PrintWriter mockedPrintWriter;
    private ConnectionService mockedConnectionService;

    @Before
    public void setUp() throws Exception {
        mockedBufferedReader = Mockito.mock(BufferedReader.class);
        Mockito.doReturn("Welcome").when(mockedBufferedReader).readLine();

        mockedPrintWriter = Mockito.mock(PrintWriter.class);

        mockedSocket = Mockito.spy(new Socket());
        Mockito.doReturn(true).when(mockedSocket).isConnected();

        mockedConnectionService = Mockito.spy(new ConnectionService());
        Mockito.doReturn(mockedSocket).when(mockedConnectionService).createSocket();
        Mockito.doReturn(mockedBufferedReader).when(mockedConnectionService).getSocketBufferedReader();
        Mockito.doReturn(mockedPrintWriter).when(mockedConnectionService).getSocketPrintWriter();

        PowerMockito.mockStatic(Platform.class);
        PowerMockito.when(Platform.isFxApplicationThread()).thenReturn(true);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSuccessfulConnection() throws Exception {
        mockedConnectionService.setHost("127.0.0.1");
        mockedConnectionService.setUsername("user");
        mockedConnectionService.setPassword("password");
        mockedConnectionService.setPort(4000);

        mockedConnectionService.start();
        while (!mockedSocket.isClosed()) {}

        try {
            Mockito.verify(mockedBufferedReader).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Mockito.verify(mockedPrintWriter).println("user password");
        Mockito.verify(mockedPrintWriter).flush();
    }
}