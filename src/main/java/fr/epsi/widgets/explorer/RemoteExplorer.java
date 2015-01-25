package fr.epsi.widgets.explorer;

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
    public void initializeNodes() {
        getRoot().setExpanded(false);
        getRoot().getChildren().add(new TreeItem<String>());
    }
}
