package fr.epsi.service.transfer.thread;

import fr.epsi.dto.FileDTO;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class UploadThread extends TransferThread {

    private Socket socket;
    private BufferedOutputStream bufferedOutputStream;
    private FileInputStream fileInputStream;

    public UploadThread(FileDTO fileDTO, Socket socket, BufferedOutputStream bufferedOutputStream, FileInputStream fileInputStream) {
        super(fileDTO);

        this.socket = socket;
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

            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }

        progress = 1.0;

        super.run();
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }
}
