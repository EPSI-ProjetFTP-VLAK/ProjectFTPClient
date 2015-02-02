package fr.epsi.service.transfer.thread;

import fr.epsi.dto.FileDTO;
import org.apache.commons.io.IOUtils;

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
        try {
            IOUtils.copy(fileInputStream, bufferedOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // progress = ((double) byteCount / fileDTO.getFile().length());

        progress = 1.0;

        super.run();
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }
}
