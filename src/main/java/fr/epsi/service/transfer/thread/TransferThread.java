package fr.epsi.service.transfer.thread;

import fr.epsi.dto.FileDTO;

public class TransferThread extends Thread {

    protected FileDTO fileDTO;
    protected String file;
    protected double progress;
    protected double bandwidth;
    protected double size;
    protected String destination;

    public TransferThread(FileDTO fileDTO) {
        this.fileDTO = fileDTO;

        file = fileDTO.getName();
        progress = 0;
        bandwidth = 0;
        size = fileDTO.getFile().getTotalSpace();
        destination = fileDTO.getDestination().toString();
    }

    @Override
    public void run() {
        progress = 1;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
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
}
