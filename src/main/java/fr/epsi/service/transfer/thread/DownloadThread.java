package fr.epsi.service.transfer.thread;

import fr.epsi.dto.FileDTO;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DownloadThread extends TransferThread {

    private BufferedInputStream bufferedInputStream;
    private FileOutputStream fileOutputStream;

    public DownloadThread(FileDTO fileDTO, BufferedInputStream bufferedInputStream, FileOutputStream fileOutputStream) {
        super(fileDTO);

        this.bufferedInputStream = bufferedInputStream;
        this.fileOutputStream = fileOutputStream;
    }

    @Override
    public void run() {
        try {
            IOUtils.copy(bufferedInputStream, fileOutputStream);

            // progress = ((double) byteCount / fileDTO.getFile().length());
        } catch (IOException e) {
            e.printStackTrace();
        }

        progress = 1.0;

        super.run();
    }

    @Override
    public void interrupt() {
        super.interrupt();

        fileDTO.getDestination().delete();
    }
}
