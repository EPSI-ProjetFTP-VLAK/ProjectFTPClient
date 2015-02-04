package fr.epsi.service.transfer.thread;

import fr.epsi.dto.FileDTO;

import java.io.*;
import java.net.Socket;

public class UploadThread extends TransferThread {

    public UploadThread(FileDTO fileDTO, Socket socket) {
        super(fileDTO, socket);
    }

    @Override
    public void run() {
        try {
            PrintWriter printWriter = getSocketPrintWriter();
            printWriter.println("up::--::" + fileDTO.getName());
            printWriter.flush();

            //IOUtils.copy(fileInputStream, new DataOutputStream(socket.getOutputStream()));

            byte[] buffer = new byte[4096];
            int currentByteCount;
            long byteCount = 0;
            DataOutputStream dataOutputStream = getSocketDataOutputStream();
            FileInputStream fileInputStream = getFileInputStream();
            long fileSize = fileInputStream.available();
            while ((currentByteCount = fileInputStream.read(buffer)) != -1 && !isInterrupted()) {
                dataOutputStream.write(buffer, 0, currentByteCount);
                dataOutputStream.flush();

                byteCount += (long) currentByteCount;
                progress = ((double) byteCount / (double) fileSize);
            }

            socket.shutdownOutput();

            BufferedReader stringIn = getSocketBufferedReader();
            String[] socketResponse = stringIn.readLine().split(":");

            progress = 1.0;

            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.run();
    }

    public DataOutputStream getSocketDataOutputStream() throws IOException {
        return new DataOutputStream(socket.getOutputStream());
    }

    public FileInputStream getFileInputStream() throws IOException {
        return new FileInputStream(fileDTO.getFile());
    }

    public BufferedReader getSocketBufferedReader() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }
}
