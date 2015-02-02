package fr.epsi.service.transfer;

import fr.epsi.controller.MainController;
import fr.epsi.dto.FileDTO;
import fr.epsi.service.FTPService;
import fr.epsi.service.connection.ConnectionState;
import fr.epsi.service.transfer.thread.DownloadThread;
import fr.epsi.service.transfer.thread.TransferThread;
import fr.epsi.widgets.transfer.TransferQueue;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
                while (!isCancelled()) {
                    downloadNextFile();

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {

                    }
                }

                disconnect();
                updateMessage(ConnectionState.DISCONNECTED.toString());

                return null;
            }

            private void downloadNextFile() {
                if (downloadQueue.size() > 0) {
                    FileDTO file = downloadQueue.poll();

                    try {
                        TransferThread downloadThread = createDownloadThread(file);
                        transferQueue.getTransferThreads().add(downloadThread);

                        downloadThread.start();
                    } catch (IOException e) {
                        console.appendText("Connection error !\n");
                        e.printStackTrace();
                    }


                    console.appendText("Downloading " + file.getName() + "...\n");
                }
            }
        };
    }

    public DownloadThread createDownloadThread(FileDTO fileDTO) throws IOException {
        return new DownloadThread(fileDTO, getSocketBufferedInputStream(), getFileOutputStream(fileDTO));
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

    public synchronized Queue<FileDTO> getDownloadQueue() {
        return downloadQueue;
    }

    public synchronized void setDownloadQueue(Queue<FileDTO> downloadQueue) {
        this.downloadQueue = downloadQueue;
    }

    public synchronized TransferQueue getTransferQueue() {
        return transferQueue;
    }

    public synchronized void setTransferQueue(TransferQueue transferQueue) {
        this.transferQueue = transferQueue;
    }
}
