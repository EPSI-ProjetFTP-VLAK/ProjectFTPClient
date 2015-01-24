package fr.epsi.dto;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FileDTO implements Serializable {

    private String name;
    private File file;
    private List<FileDTO> children = new ArrayList<FileDTO>();
    private boolean isDirectory;
    private File destination;

    public FileDTO() {}

    public FileDTO(File file, boolean recursive) {
        this.name = file.getName();
        this.file = file;
        this.isDirectory = file.isDirectory();

        if (isDirectory() && recursive) {
            try {
                for (File child : file.listFiles()) {
                    this.children.add(new FileDTO(child, false));
                }
            } catch (NullPointerException e) {

            }
        }
    }

    public FileDTO(File file) {
        this(file, true);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
    }

    public List<FileDTO> getChildren() {
        return children;
    }

    public void setChildren(List<FileDTO> children) {
        this.children = children;
    }

    public File getDestination() {
        return destination;
    }

    public void setDestination(File destination) {
        this.destination = destination;
    }
}
