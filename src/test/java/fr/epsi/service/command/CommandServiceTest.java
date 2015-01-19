package fr.epsi.service.command;

import fr.epsi.service.FTPServiceTest;
import javafx.application.Platform;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

@PrepareForTest(Platform.class)
@RunWith(PowerMockRunner.class)
public class CommandServiceTest extends FTPServiceTest {
    private Queue<FTPCommand> commandQueue;
    private CommandService mockedCommandService;

    public CommandServiceTest() {
        super(Mockito.spy(new CommandService()));

        mockedCommandService = (CommandService) mockedService;

        commandQueue = Mockito.spy(new LinkedList<FTPCommand>());
        mockedCommandService.setCommandQueue(commandQueue);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        mockedSocket = Mockito.mock(Socket.class);
        mockedCommandService.setSocket(mockedSocket);
    }

    @Test
    public void testOneCommand() throws Exception {
        FTPCommand mockedFtpCommand = Mockito.mock(FTPCommand.class);
        Mockito.when(mockedFtpCommand.isExecuted()).thenReturn(true);

        commandQueue.offer(mockedFtpCommand);

        mockedCommandService.start();
        while (commandQueue.size() > 0) {}

        Mockito.verify(commandQueue, Mockito.times(1)).poll();
        Mockito.verify(mockedFtpCommand, Mockito.times(1)).execute(mockedSocket);
    }

    @Test
    public void testMultipleCommands() throws Exception {
        FTPCommand[] ftpCommands = new FTPCommand[5];
        for (int i = 0; i < 5; i++) {
            FTPCommand mockedFtpCommand = Mockito.mock(FTPCommand.class);
            Mockito.when(mockedFtpCommand.isExecuted()).thenReturn(true);

            commandQueue.offer(mockedFtpCommand);
            ftpCommands[i] = mockedFtpCommand;
        }

        mockedCommandService.start();
        while (commandQueue.size() > 0) {}

        Mockito.verify(commandQueue, Mockito.times(5)).poll();

        for (FTPCommand ftpCommand : ftpCommands) {
            Mockito.verify(ftpCommand, Mockito.times(1)).execute(mockedSocket);
        }
    }
}