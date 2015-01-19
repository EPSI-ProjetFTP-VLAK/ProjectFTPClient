package fr.epsi.service.command;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

@PrepareForTest(Platform.class)
@RunWith(PowerMockRunner.class)
public class CommandServiceTest {
    private TextArea mockedConsole;
    private Socket mockedSocket;
    private BufferedReader mockedBufferedReader;
    private PrintWriter mockedPrintWriter;
    private CommandService mockedCommandService;

    @Before
    public void setUp() throws Exception {
//        mockedConsole = Mockito.mock(TextArea.class);
//
//        mockedBufferedReader = Mockito.mock(BufferedReader.class);
//        Mockito.doReturn("Welcome").doReturn("AUTH : OK").when(mockedBufferedReader).readLine();
//
//        mockedPrintWriter = Mockito.mock(PrintWriter.class);
//
//        mockedSocket = Mockito.spy(new Socket());
//        Mockito.doReturn(true).when(mockedSocket).isConnected();
//
//        mockedCommandService = Mockito.spy(new CommandService(mockedSocket, mockedConsole));
//        Mockito.doReturn(mockedBufferedReader).when(mockedCommandService).getSocketBufferedReader();
//        Mockito.doReturn(mockedPrintWriter).when(mockedCommandService).getSocketPrintWriter();
//
//        PowerMockito.mockStatic(Platform.class);
//        PowerMockito.when(Platform.isFxApplicationThread()).thenReturn(true);
    }

    @After
    public void tearDown() throws Exception {
//        mockedCommandService.cancel();
//
//        Mockito.verify(mockedPrintWriter).println("exit");
//        Mockito.verify(mockedPrintWriter, Mockito.times(2)).flush();
//
//        Mockito.verify(mockedConsole).appendText("Disconnecting...");
//        Mockito.verify(mockedSocket).close();
    }

    @Test
    public void testSendCommand() throws Exception {

    }
}