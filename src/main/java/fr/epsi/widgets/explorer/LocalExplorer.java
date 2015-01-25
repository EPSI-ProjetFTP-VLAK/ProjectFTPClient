package fr.epsi.widgets.explorer;

import fr.epsi.controller.MainController;
import fr.epsi.dto.FileDTO;
import fr.epsi.service.command.commands.DownloadCommand;
import fr.epsi.widgets.explorer.filetree.FileTreeItem;
import fr.epsi.widgets.explorer.filetree.local.LocalFileTreeItem;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class LocalExplorer extends AbstractExplorer {

    public LocalExplorer() {
        super();
    }

    @Override
    public void doOnFileDrag(MouseEvent mouseEvent) {

    }

    @Override
    public void doOnFileDrop(FileTreeItem sourceFileTreeItem, FileTreeItem targetFileTreeItem, DragEvent dragEvent) {
        FileDTO fileDTO = sourceFileTreeItem.getFileDTO();
        fileDTO.setDestination(new File(targetFileTreeItem.getFileDTO().getFile().toPath().toString() + System.getProperty("file.separator") + fileDTO.getName()));

        DownloadCommand downloadCommand = createDownloadCommand(fileDTO);
        MainController.getCommandService().getCommandQueue().offer(downloadCommand);
    }

    public DownloadCommand createDownloadCommand(FileDTO fileDTO) {
        return new DownloadCommand(fileDTO);
    }

    @Override
    public void initializeNodes() {
        File[] rootDirectories = FileSystemView.getFileSystemView().getRoots();
        for (File rootDirectory : rootDirectories) {
            FileTreeItem treeNode = new LocalFileTreeItem(new FileDTO(rootDirectory));
            getRoot().getChildren().add(treeNode);
        }
    }
}
