package fr.epsi.service.transfer.thread;

import fr.epsi.dto.FileDTO;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class TransferThread extends Thread {

    protected FileDTO fileDTO;
    protected Socket socket;
    protected String file;
    protected DoubleProperty progress = new SimpleDoubleProperty(0, "progress");
    protected double bandwidth;
    protected double size;
    protected String destination;

    public TransferThread(FileDTO fileDTO, Socket socket) {
        this.fileDTO = fileDTO;
        this.socket = socket;

        file = fileDTO.getName();
        bandwidth = 0;
        size = fileDTO.getFile().getTotalSpace();
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
        return bandwidth;
    }

    public void setBandwidth(double bandwith) {
        this.bandwidth = bandwith;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
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
