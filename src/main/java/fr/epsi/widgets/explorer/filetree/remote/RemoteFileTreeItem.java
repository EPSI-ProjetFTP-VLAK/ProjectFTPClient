package fr.epsi.widgets.explorer.filetree.remote;

import fr.epsi.controller.MainController;
import fr.epsi.dto.FileDTO;
import fr.epsi.service.command.CommandService;
import fr.epsi.service.command.commands.CdCommand;
import fr.epsi.widgets.explorer.filetree.FileTreeItem;

public class RemoteFileTreeItem extends FileTreeItem {

    public RemoteFileTreeItem(FileDTO file) {
        super(file);
    }

    @Override
    public void generateChildNodes() {
        if (isSocketConnect()) {
            CdCommand cdCommand = createCdCommand();

            CommandService commandService = MainController.getCommandService();
            commandService.getCommandQueue().offer(cdCommand);

            while (commandService.getCommandQueue().contains(cdCommand)) {}

            file = (FileDTO) cdCommand.getResponse();

            for (FileDTO subFile : file.getChildren()) {
                getChildren().add(new RemoteFileTreeItem(subFile));
            }
        }
    }

    public boolean isSocketConnect() {
        return MainController.getConnectionService().getSocket() != null && MainController.getConnectionService().getSocket().isConnected();
    }

    public CdCommand createCdCommand() {
        return new CdCommand(file.toString());
    }
}
