package fr.epsi.service.transfer.thread;

import fr.epsi.dto.FileDTO;

import java.io.*;

public class UploadThread extends TransferThread {

    private BufferedOutputStream bufferedOutputStream;
    private FileInputStream fileInputStream;

    public UploadThread(FileDTO fileDTO, BufferedOutputStream bufferedOutputStream, FileInputStream fileInputStream) {
        super(fileDTO);

        this.bufferedOutputStream = bufferedOutputStream;
        this.fileInputStream = fileInputStream;
    }

    @Override
    public void run() {
        byte[] bytes = new byte[1024];
        int length = 0;
        int byteCount = 1024;

        try {
            while ((length = fileInputStream.read(bytes, 0, 1024)) != -1) {
                byteCount += 1024;
                bufferedOutputStream.write(bytes, 0, length);
                bufferedOutputStream.flush();

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
    }
}
