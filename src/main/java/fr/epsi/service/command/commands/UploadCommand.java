package fr.epsi.service.command.commands;

import fr.epsi.controller.MainController;
import fr.epsi.dto.FileDTO;

public class UploadCommand extends FTPCommand {

    private FileDTO file;

    public UploadCommand() {
    }

    public UploadCommand(FileDTO file) {
        super();

        this.file = file;
    }

    @Override
    public void execute() throws Exception {
        MainController.getUploadService().getUploadQueue().offer(file);
        while (MainController.getUploadService().getUploadQueue().contains(file)) {}

        super.execute();
    }

    public FileDTO getFile() {
        return file;
    }

    public void setFile(FileDTO file) {
        this.file = file;
    }
}
