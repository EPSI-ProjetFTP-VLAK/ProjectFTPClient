package fr.epsi.service.transfer.thread;

import fr.epsi.dto.FileDTO;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class TransferThreadTest {

    protected File mockedSourceFile;
    protected File mockedDestinationFile;
    protected FileDTO mockedFileDTO;
    protected TransferThread transferThread;

    @Before
    public void setUp() throws Exception {
        mockedSourceFile = Mockito.mock(File.class);
        mockedDestinationFile = Mockito.mock(File.class);

        mockedFileDTO = Mockito.spy(new FileDTO(mockedSourceFile));
        Mockito.doReturn(mockedDestinationFile).when(mockedFileDTO).getDestination();

        transferThread = new TransferThread(mockedFileDTO);
    }

    @After
    public void tearDown() throws Exception {
        assertEquals(1, transferThread.getProgress(), 1);
    }
}