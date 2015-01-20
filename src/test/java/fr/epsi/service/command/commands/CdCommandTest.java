package fr.epsi.service.command.commands;

import org.mockito.Mockito;

public class CdCommandTest extends LsCommandTest {

    public static String DIRECTORY = "test-folder";

    public CdCommandTest() {
        super(Mockito.spy(new CdCommand(DIRECTORY)));
    }

    @Override
    public void testExecuteWithFourFiles() throws Exception {
        super.testExecuteWithFourFiles();

        Mockito.verify(mockedPrintWriter).println("cd" + FTPCommand.SEPARATOR + DIRECTORY);
        Mockito.verify(mockedPrintWriter, Mockito.atLeast(2)).flush();
    }
}