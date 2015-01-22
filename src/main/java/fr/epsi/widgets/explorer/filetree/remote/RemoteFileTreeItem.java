package fr.epsi.widgets.explorer.filetree.remote;

import fr.epsi.controller.MainController;
import fr.epsi.service.command.CommandService;
import fr.epsi.service.command.commands.CdCommand;
import fr.epsi.widgets.explorer.filetree.FileTreeItem;

import java.io.File;

public class RemoteFileTreeItem extends FileTreeItem {

    public RemoteFileTreeItem(File file) {
        super(file);
    }

    @Override
    public void generateChildNodes() {
        if (isSocketConnect()) {
            CdCommand cdCommand = createCdCommand();

            CommandService commandService = MainController.getCommandService();
            commandService.getCommandQueue().offer(cdCommand);

            while (commandService.getCommandQueue().contains(cdCommand)) {}

            file = (File) cdCommand.getResponse();

            for (File subFile : file.listFiles()) {
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
