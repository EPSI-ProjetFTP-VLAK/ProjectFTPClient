package fr.epsi.service.command.commands;

import fr.epsi.controller.MainController;
import fr.epsi.dto.FileDTO;

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
        MainController.getDownloadService().getDownloadQueue().offer(file);
        while (MainController.getDownloadService().getDownloadQueue().contains(file)) {}

        super.execute();
    }

    public FileDTO getFile() {
        return file;
    }

    public void setFile(FileDTO file) {
        this.file = file;
    }
}
