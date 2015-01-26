package fr.epsi.dto;

public class TransferDTO {

    private String file;
    private double progress;
    private double bandwidth;
    private double size;
    private String destination;

    public TransferDTO(FileDTO fileDTO) {
        file = fileDTO.getName();
        progress = 0;
        bandwidth = 0;
        size = fileDTO.getFile().getTotalSpace();
        destination = fileDTO.getDestination().toString();
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
