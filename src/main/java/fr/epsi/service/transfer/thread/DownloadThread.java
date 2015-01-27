package fr.epsi.service.transfer.thread;

import fr.epsi.dto.FileDTO;

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
        byte[] bytes = new byte[1024];
        int length = 0;
        int byteCount = 1024;

        try {
            while ((length = bufferedInputStream.read(bytes, 0, 1024)) != -1 && !isInterrupted()) {
                byteCount += 1024;
                fileOutputStream.write(bytes, 0, length);

                progress = ((double) byteCount / fileDTO.getFile().length());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.run();
    }

    @Override
    public void interrupt() {
        super.interrupt();

        fileDTO.getDestination().delete();
    }
}
