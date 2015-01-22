package fr.epsi.service.command.commands;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FTPCommandTest {
    protected FTPCommand mockedFtpCommand;
    protected Socket mockedSocket;
    protected BufferedReader mockedBufferedReader;
    protected PrintWriter mockedPrintWriter;
    protected ObjectOutputStream mockedObjectOutputStream;
    protected ObjectInputStream mockedObjectInputStream;

    public FTPCommandTest() {
        this.mockedFtpCommand = Mockito.spy(new FTPCommand() {});
    }

    public FTPCommandTest(FTPCommand mockedFtpCommand) {
        this.mockedFtpCommand = mockedFtpCommand;
    }

    @Before
    public void setUp() throws Exception {
        mockedBufferedReader = Mockito.mock(BufferedReader.class);
        mockedPrintWriter = Mockito.mock(PrintWriter.class);
        mockedObjectInputStream = Mockito.mock(ObjectInputStream.class);
        mockedObjectOutputStream = Mockito.mock(ObjectOutputStream.class);
        mockedSocket = Mockito.spy(new Socket());

        Mockito.doReturn(true).when(mockedSocket).isConnected();

        Mockito.doReturn(mockedBufferedReader).when(mockedFtpCommand).getSocketBufferedReader();
        Mockito.doReturn(mockedPrintWriter).when(mockedFtpCommand).getSocketPrintWriter();
        Mockito.doReturn(mockedObjectOutputStream).when(mockedFtpCommand).getSocketObjectOutputStream();
        Mockito.doReturn(mockedObjectInputStream).when(mockedFtpCommand).getSocketObjectInputStream();

        assertFalse(mockedFtpCommand.isExecuted());
    }

    @After
    public void tearDown() throws Exception {
        mockedSocket.close();

        assertTrue(mockedFtpCommand.isExecuted());
    }

    @Test
    public void testExecute() throws Exception {
        mockedFtpCommand.execute();
    }
}