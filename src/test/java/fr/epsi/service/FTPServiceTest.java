package fr.epsi.service;

import fr.epsi.service.connection.ConnectionState;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

@PrepareForTest(Platform.class)
@RunWith(PowerMockRunner.class)
public abstract class FTPServiceTest {
    protected TextArea mockedConsole;
    protected Socket mockedSocket;
    protected BufferedReader mockedBufferedReader;
    protected PrintWriter mockedPrintWriter;
    protected FTPService mockedService;

    public FTPServiceTest(FTPService mockedService) {
        this.mockedService = mockedService;
    }

    @Before
    public void setUp() throws Exception {
        mockedConsole = Mockito.mock(TextArea.class);
        mockedBufferedReader = Mockito.mock(BufferedReader.class);
        mockedPrintWriter = Mockito.mock(PrintWriter.class);
        mockedSocket = Mockito.spy(new Socket());

        mockedService.setConsole(mockedConsole);

        Mockito.doReturn(true).when(mockedSocket).isConnected();

        Mockito.doReturn(mockedBufferedReader).when(mockedService).getSocketBufferedReader();
        Mockito.doReturn(mockedPrintWriter).when(mockedService).getSocketPrintWriter();

        PowerMockito.mockStatic(Platform.class);
        PowerMockito.when(Platform.isFxApplicationThread()).thenReturn(true);
    }

    @After
    public void tearDown() throws Exception {
        mockedService.cancel();
        while (!mockedService.getMessage().equals(ConnectionState.DISCONNECTED.toString())) {}

        Mockito.verify(mockedPrintWriter).println("exit");
        Mockito.verify(mockedPrintWriter, Mockito.atLeast(1)).flush();

        Mockito.verify(mockedConsole).appendText("Disconnecting...");
        Mockito.verify(mockedSocket).close();
    }
}