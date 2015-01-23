package fr.epsi.service.command.commands;

import fr.epsi.controller.MainController;
import fr.epsi.dto.FileDTO;

import java.io.IOException;
import java.io.PrintWriter;

public class DownloadCommand extends FTPCommand {

    private FileDTO file;

    public DownloadCommand() {
    }

    public DownloadCommand(FileDTO file) {
        super();

        this.file = file;
    }

    @Override
    public void execute() throws Exception {
        try {
            PrintWriter out = getSocketPrintWriter();
            out.println("down" + SEPARATOR + file.getName());
            out.flush();

            MainController.getDownloadService().getDownloadQueue().offer(file);
            while (MainController.getDownloadService().getDownloadQueue().contains(file)) {}


        } catch (IOException e) {
            e.printStackTrace();
        }

        super.execute();
    }

    public FileDTO getFile() {
        return file;
    }

    public void setFile(FileDTO file) {
        this.file = file;
    }
}
