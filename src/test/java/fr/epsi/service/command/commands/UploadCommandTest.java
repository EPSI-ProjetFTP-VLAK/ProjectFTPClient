package fr.epsi.service.command.commands;

import fr.epsi.controller.MainController;
import fr.epsi.dto.FileDTO;
import fr.epsi.service.transfer.UploadService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.util.PriorityQueue;
import java.util.Queue;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MainController.class)
public class UploadCommandTest extends FTPCommandTest {

    private FileDTO mockedFileDTO;
    private UploadService mockedUploadService;
    private Queue<FileDTO> mockedDownloadQueue;

    public UploadCommandTest() {
        super(Mockito.spy(new UploadCommand()));
    }

    public UploadCommandTest(FTPCommand mockedFtpCommand) {
        super(mockedFtpCommand);
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        mockedFileDTO = Mockito.spy(new FileDTO(new File("test-download-file")));

        mockedDownloadQueue = Mockito.spy(new PriorityQueue<FileDTO>());
        Mockito.doReturn(true).doReturn(false).when(mockedDownloadQueue).contains(mockedFileDTO);

        mockedUploadService = Mockito.mock(UploadService.class);
        PowerMockito.when(mockedUploadService.getUploadQueue()).thenReturn(mockedDownloadQueue);

        PowerMockito.mockStatic(MainController.class);
        PowerMockito.when(MainController.getUploadService()).thenReturn(mockedUploadService);
    }

    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testExecuteDownloadOneFile() throws Exception {
        ((UploadCommand) mockedFtpCommand).setFile(mockedFileDTO);

        mockedFtpCommand.execute();

        Mockito.verify(mockedDownloadQueue, Mockito.times(1)).offer(mockedFileDTO);

        Mockito.verify(mockedDownloadQueue, Mockito.atLeast(1)).contains(mockedFileDTO);
    }

    @Override
    public void testExecute() throws Exception {

    }
}