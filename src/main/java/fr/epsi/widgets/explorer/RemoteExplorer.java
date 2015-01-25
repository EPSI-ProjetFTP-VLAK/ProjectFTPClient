package fr.epsi.widgets.explorer;

import fr.epsi.dto.FileDTO;
import fr.epsi.widgets.explorer.filetree.FileTreeItem;
import fr.epsi.widgets.explorer.filetree.local.LocalFileTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

import java.io.File;

public class RemoteExplorer extends AbstractExplorer {
    @Override
    public void doOnFileDrag(MouseEvent mouseEvent) {

    }

    @Override
    public void doOnFileDrop(FileTreeItem fileTreeItem, DragEvent dragEvent) {
        System.out.println("up");
    }

    @Override
    public void initializeNodes() {
        getRoot().setExpanded(false);
        getRoot().getChildren().add(new TreeItem<String>());

        getRoot().getChildren().add(new LocalFileTreeItem(new FileDTO(new File("Test"))));
    }
}
