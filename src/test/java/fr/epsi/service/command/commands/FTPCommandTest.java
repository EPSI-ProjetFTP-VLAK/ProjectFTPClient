package fr.epsi.service.command.commands;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.Socket;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FTPCommandTest {
    protected FTPCommand mockedFtpCommand;
    protected Socket mockedSocket;

    @Before
    public void setUp() throws Exception {
        mockedFtpCommand = Mockito.spy(new FTPCommand() {});
        mockedSocket = Mockito.mock(Socket.class);

        assertFalse(mockedFtpCommand.isExecuted());
    }

    @After
    public void tearDown() throws Exception {
        assertTrue(mockedFtpCommand.isExecuted());
    }

    @Test
    public void testExecute() throws Exception {
        mockedFtpCommand.execute(mockedSocket);
    }
}