package fr.epsi.service.transfer.thread;

import fr.epsi.dto.FileDTO;
import javafx.beans.property.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class TransferThread extends Thread {

    protected FileDTO fileDTO;
    protected Socket socket;
    protected String file;
    protected DoubleProperty progress = new SimpleDoubleProperty(0, "progress");
    protected LongProperty transferred =  new SimpleLongProperty(0, "transferred");
    protected LongProperty fileSize =  new SimpleLongProperty(0, "fileSize");
    protected String destination;

    public TransferThread(FileDTO fileDTO, Socket socket) {
        this.fileDTO = fileDTO;
        this.socket = socket;

        file = fileDTO.getName();
        destination = fileDTO.getDestination().toString();
    }

    @Override
    public void run() {
        progress.set(1.0);
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public double getProgress() {
        return progress.get();
    }

    public void setProgress(double progress) {
        this.progress.set(progress);
    }

    public ReadOnlyDoubleProperty progressProperty() {
        return progress;
    }

    public double getBandwidvth() {
        return transferred.get();
    }

    public void setTransferred(long transferred) {
        this.transferred.set(transferred);
    }

    public ReadOnlyLongProperty transferredProperty() {
        return transferred;
    }

    public double getFileSize() {
        return fileSize.get();
    }

    public void setFileSize(long fileSize) {
        this.fileSize.set(fileSize);
    }

    public ReadOnlyLongProperty fileSizeProperty() {
        return fileSize;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public PrintWriter getSocketPrintWriter() throws IOException {
        return new PrintWriter(socket.getOutputStream());
    }
}
