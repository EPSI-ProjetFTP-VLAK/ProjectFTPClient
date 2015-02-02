package fr.epsi.service.command.commands;

import org.junit.Before;
import org.mockito.Mockito;

public class CdCommandTest extends LsCommandTest {

    public static String DIRECTORY = "test-folder";

    public CdCommandTest() {
        super(Mockito.spy(new CdCommand(DIRECTORY)));
    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        Mockito.doReturn("/home/test-directory").doReturn("ls:" + mockedFiles.length).when(mockedBufferedReader).readLine();
    }

    @Override
    public void testExecuteWithFourFiles() throws Exception {
        super.testExecuteWithFourFiles();

        Mockito.verify(mockedPrintWriter).println("cd" + FTPCommand.SEPARATOR + DIRECTORY);
        Mockito.verify(mockedPrintWriter, Mockito.atLeast(2)).flush();

        Mockito.verify(mockedBufferedReader, Mockito.atLeast(2)).readLine();
    }
}