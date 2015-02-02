package fr.epsi.widgets.explorer;

import fr.epsi.controller.MainController;
import fr.epsi.dto.FileDTO;
import fr.epsi.service.command.commands.RmCommand;
import fr.epsi.widgets.explorer.filetree.FileTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

public class RemoteExplorer extends AbstractExplorer {
    @Override
    public void doOnFileDrag(MouseEvent mouseEvent) {

    }

    @Override
    public void doOnFileDrop(FileTreeItem sourceFileTreeItem, FileTreeItem targetFileTreeItem, DragEvent dragEvent) {
        System.out.println("up");
    }

    @Override
    public void doOnDeleteEvent(FileDTO fileDTO) {
        RmCommand rmCommand = createRmDirCommand(fileDTO);

        MainController.getCommandService().getCommandQueue().offer(rmCommand);
    }

    public RmCommand createRmDirCommand(FileDTO fileDTO) {
        return new RmCommand(fileDTO);
    }

    @Override
    public void initializeNodes() {
        getRoot().setExpanded(false);
        getRoot().getChildren().add(new TreeItem<String>());
    }
}
