package fr.epsi.service.transfer;

import fr.epsi.controller.MainController;
import fr.epsi.dto.FileDTO;
import fr.epsi.dto.TransferDTO;
import fr.epsi.service.FTPService;
import fr.epsi.service.connection.ConnectionState;
import fr.epsi.widgets.transfer.TransferQueue;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.Socket;
import java.util.PriorityQueue;
import java.util.Queue;

public class DownloadService extends FTPService {
    private TransferQueue transferQueue;
    private Queue<FileDTO> downloadQueue = new PriorityQueue<>();

    public DownloadService() {}

    public DownloadService(Socket socket, TextArea console, TransferQueue transferQueue) {
        super(socket, console);

        this.transferQueue = transferQueue;
    }

    @Override
    protected Task<Void> createTask()
    {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    while (!isCancelled()) {
                        downloadNextFile();

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {

                        }
                    }

                    disconnect();
                    updateMessage(ConnectionState.DISCONNECTED.toString());
                } catch (Exception e) {
                    console.appendText(e.getMessage() + " !");
                    e.printStackTrace();
                }

                return null;
            }

            private void downloadNextFile() throws IOException {
                if (downloadQueue.size() > 0) {
                    FileDTO file = downloadQueue.poll();

                    TransferDTO transferDTO = createTransferDTO(file);
                    transferQueue.getTransferDTOs().add(transferDTO);

                    console.appendText("Downloading " + file.getName() + "...");

                    byte[] bytes = new byte[1024];
                    int length = 0;
                    int byteCount = 1024;

                    FileOutputStream outputFileStream = getFileOutputStream(file);
                    BufferedInputStream bufferedInputStream = getSocketBufferedInputStream();

                    while ((length = bufferedInputStream.read(bytes, 0, 1024)) != -1) {
                        byteCount += 1024;
                        outputFileStream.write(bytes, 0, length);

                        transferDTO.setProgress((double) byteCount / file.getFile().length());
                    }

                    transferDTO.setProgress(1);
                }
            }
        };
    }

    public TransferDTO createTransferDTO(FileDTO file) {
        return new TransferDTO(file);
    }

    public BufferedInputStream getSocketBufferedInputStream() throws IOException {
        return new BufferedInputStream(getSocket().getInputStream(), 1024);
    }

    public FileOutputStream getFileOutputStream(FileDTO file) throws IOException {
        return new FileOutputStream(file.getDestination());
    }

    @Override
    public Socket getSocket() {
        if (socket == null) {
            socket = createSocket();
        }

        return socket;
    }

    public Socket createSocket() {
        Socket socket = null;
        try {
            socket = new Socket(MainController.getConnectionService().getHost(), MainController.getConnectionService().getPort() + 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return socket;
    }

    public Queue<FileDTO> getDownloadQueue() {
        return downloadQueue;
    }

    public void setDownloadQueue(Queue<FileDTO> downloadQueue) {
        this.downloadQueue = downloadQueue;
    }

    public TransferQueue getTransferQueue() {
        return transferQueue;
    }

    public void setTransferQueue(TransferQueue transferQueue) {
        this.transferQueue = transferQueue;
    }
}
