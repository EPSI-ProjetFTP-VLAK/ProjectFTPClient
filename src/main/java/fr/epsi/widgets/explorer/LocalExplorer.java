package fr.epsi.widgets.explorer;

import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class LocalExplorer extends AbstractExplorer {

    public LocalExplorer() {
        super();
    }

    @Override
    public void generateChildNodes() throws Exception {
        super.generateChildNodes();

        File[] rootDirectories = FileSystemView.getFileSystemView().getRoots();
        for (File rootDirectory : rootDirectories) {
            FileTreeItem treeNode = new FileTreeItem(rootDirectory);
            getRoot().getChildren().add(treeNode);
        }
    }

    @Override
    public void doOnDrag(MouseEvent mouseEvent) {

    }

    @Override
    public void doOnDrop(DragEvent dragEvent) {

    }

    @Override
    public void doOnClick(MouseEvent mouseEvent) {

    }
}
