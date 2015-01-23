package fr.epsi.widgets.explorer;

import fr.epsi.dto.FileDTO;
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
    public void doOnFileDrop(DragEvent dragEvent) {
        System.out.println("up");
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
