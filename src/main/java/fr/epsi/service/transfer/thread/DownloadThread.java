package fr.epsi.service.transfer.thread;

import fr.epsi.dto.FileDTO;

import java.io.*;
import java.net.Socket;

public class DownloadThread extends TransferThread {

    public DownloadThread(FileDTO fileDTO, Socket socket) {
        super(fileDTO, socket);
    }

    @Override
    public void run() {
        try {
            PrintWriter printWriter = getSocketPrintWriter();
            printWriter.println("down::--::" + fileDTO.getName());
            printWriter.flush();

            fileDTO.getDestination().createNewFile();
            fileDTO.getDestination().setWritable(true, false);

            BufferedInputStream bufferedInputStream = getSocketBufferedInputStream();
            FileOutputStream fileOutputStream = getFileOutputStream();

            byte[] buffer = new byte[4096];
            int currentByteCount;
            long byteCount = 0;
            long fileSize = bufferedInputStream.available();
            while((currentByteCount = bufferedInputStream.read(buffer)) != -1 && !isInterrupted()){
                fileOutputStream.write(buffer, 0, currentByteCount);

                byteCount += currentByteCount;
                progress = ((double) byteCount / (double) fileSize);
            }

            socket.shutdownInput();

            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        progress = 1.0;

        super.run();
    }

    public PrintWriter getSocketPrintWriter() throws IOException {
        return new PrintWriter(socket.getOutputStream());
    }

    public BufferedInputStream getSocketBufferedInputStream() throws IOException {
        return new BufferedInputStream(new DataInputStream(socket.getInputStream()));
    }

    public FileOutputStream getFileOutputStream() throws IOException {
        return new FileOutputStream(fileDTO.getDestination());
    }

    @Override
    public void interrupt() {
        super.interrupt();

        fileDTO.getDestination().delete();
    }
}
