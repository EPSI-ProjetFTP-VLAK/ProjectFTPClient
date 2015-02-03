package fr.epsi.service.transfer.thread;

import fr.epsi.dto.FileDTO;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;

import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class TransferThreadTest {

    protected File mockedSourceFile;
    protected File mockedDestinationFile;
    protected FileDTO mockedFileDTO;
    protected Socket mockedSocket;
    protected PrintWriter mockedPrintWriter;
    protected TransferThread mockedTransferThread;

    @Before
    public void setUp() throws Exception {
        mockedSourceFile = Mockito.mock(File.class);
        mockedDestinationFile = Mockito.mock(File.class);

        mockedFileDTO = Mockito.spy(new FileDTO(mockedSourceFile));
        Mockito.doReturn(mockedDestinationFile).when(mockedFileDTO).getDestination();

        mockedPrintWriter = Mockito.mock(PrintWriter.class);

        mockedSocket = Mockito.mock(Socket.class);

        mockedTransferThread = Mockito.spy(new TransferThread(mockedFileDTO, mockedSocket));

        Mockito.doReturn(mockedPrintWriter).when(mockedTransferThread).getSocketPrintWriter();
    }

    @After
    public void tearDown() throws Exception {
        assertEquals(1, mockedTransferThread.getProgress(), 1);
    }
}