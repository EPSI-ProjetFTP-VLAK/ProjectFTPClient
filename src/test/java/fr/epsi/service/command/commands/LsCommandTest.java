package fr.epsi.service.command.commands;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class LsCommandTest extends FTPCommandTest {
    private File[] mockedFiles;

    public LsCommandTest() {
        super(Mockito.spy(new LsCommand()));
    }

    public LsCommandTest(FTPCommand mockedFtpCommand) {
        super(mockedFtpCommand);
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        mockedFiles = new File[4];
        mockedFiles[0] = Mockito.spy(new File("file-1"));
        mockedFiles[1] = Mockito.spy(new File("file-2"));
        mockedFiles[2] = Mockito.spy(new File("file-3"));
        mockedFiles[3] = Mockito.spy(new File("file-4"));

        Mockito.doReturn("ls:" + mockedFiles.length).when(mockedBufferedReader).readLine();

        Mockito.doReturn(mockedFiles[0])
                .doReturn(mockedFiles[1])
                .doReturn(mockedFiles[2])
                .doReturn(mockedFiles[3]).when((LsCommand) mockedFtpCommand).readFileFromSocket(mockedSocket);
    }

    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testExecuteWithFourFiles() throws Exception {
        mockedFtpCommand.execute(mockedSocket);

        Mockito.verify(mockedPrintWriter).println("ls");
        Mockito.verify(mockedPrintWriter, Mockito.atLeast(1)).flush();

        Mockito.verify((LsCommand) mockedFtpCommand, Mockito.times(4)).readFileFromSocket(mockedSocket);

        File[] ftpCommandResponse = (File[]) mockedFtpCommand.getResponse();
        for (int i = 0; i < ftpCommandResponse.length; i++) {
            assertEquals(mockedFiles[i], ftpCommandResponse[i]);
        }
    }
}