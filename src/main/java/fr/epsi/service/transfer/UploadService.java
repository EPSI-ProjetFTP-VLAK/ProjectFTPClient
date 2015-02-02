package fr.epsi.service.transfer;

import fr.epsi.controller.MainController;
import fr.epsi.dto.FileDTO;
import fr.epsi.service.FTPService;
import fr.epsi.service.connection.ConnectionState;
import fr.epsi.service.transfer.thread.TransferThread;
import fr.epsi.service.transfer.thread.UploadThread;
import fr.epsi.widgets.transfer.TransferQueue;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.PriorityQueue;
import java.util.Queue;

public class UploadService extends FTPService {
    private TransferQueue transferQueue;
    private Queue<FileDTO> uploadQueue = new PriorityQueue<>();

    public UploadService() {}

    public UploadService(TextArea console, TransferQueue transferQueue) {
        super(null, console);

        this.transferQueue = transferQueue;
    }

    @Override
    protected Task<Void> createTask()
    {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (!isCancelled()) {
                    uploadNextFile();

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {

                    }
                }

                disconnect();
                updateMessage(ConnectionState.DISCONNECTED.toString());

                return null;
            }

            private void uploadNextFile() {
                if (uploadQueue.size() > 0) {
                    FileDTO file = uploadQueue.poll();

                    try {
                        TransferThread uploadThread = createUploadThread(file);
                        transferQueue.getTransferThreads().add(uploadThread);

                        uploadThread.start();
                    } catch (IOException e) {
                        console.appendText("Connection error !\n");
                        e.printStackTrace();
                    }


                    console.appendText("Downloading " + file.getName() + "...\n");
                }
            }
        };
    }

    public UploadThread createUploadThread(FileDTO fileDTO) throws IOException {
        return new UploadThread(fileDTO, getSocketBufferedOutputStream(), getFileInputStream(fileDTO));
    }

    public BufferedOutputStream getSocketBufferedOutputStream() throws IOException {
        return new BufferedOutputStream(getSocket().getOutputStream(), 1024);
    }

    public FileInputStream getFileInputStream(FileDTO file) throws IOException {
        return new FileInputStream(file.getDestination());
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
            socket = new Socket(MainController.getConnectionService().getHost(), MainController.getConnectionService().getPort() + 2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return socket;
    }

    public synchronized Queue<FileDTO> getUploadQueue() {
        return uploadQueue;
    }

    public synchronized void setUploadQueue(Queue<FileDTO> uploadQueue) {
        this.uploadQueue = uploadQueue;
    }

    public synchronized TransferQueue getTransferQueue() {
        return transferQueue;
    }

    public synchronized void setTransferQueue(TransferQueue transferQueue) {
        this.transferQueue = transferQueue;
    }
}
