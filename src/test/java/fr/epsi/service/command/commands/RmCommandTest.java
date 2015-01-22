package fr.epsi.service.command.commands;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;

public class RmCommandTest extends FTPCommandTest {

    public RmCommandTest() {
        super(Mockito.spy(new RmCommand(new File("test-file"))));
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    @Override
    public void testExecute() throws Exception {
        Mockito.doReturn("rm:0").when(mockedBufferedReader).readLine();

        mockedFtpCommand.execute();

        Mockito.verify(mockedPrintWriter).println("rm" + FTPCommand.SEPARATOR + "test-file");
        Mockito.verify(mockedPrintWriter, Mockito.atLeast(1)).flush();
    }

    @Test(expected = Exception.class)
    public void testExecuteFailed() throws Exception {
        Mockito.doReturn("rm:-1").when(mockedBufferedReader).readLine();

        mockedFtpCommand.execute();

        Mockito.verify(mockedPrintWriter).println("rm" + FTPCommand.SEPARATOR + "test-file");
        Mockito.verify(mockedPrintWriter, Mockito.atLeast(1)).flush();
    }
}