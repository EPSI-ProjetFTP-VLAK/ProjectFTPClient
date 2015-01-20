package fr.epsi.widgets.explorer;

import javafx.scene.control.TreeItem;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

public class RemoteExplorer extends AbstractExplorer {
    @Override
    public void doOnFileDrag(MouseEvent mouseEvent) {

    }

    @Override
    public void doOnFileDrop(DragEvent dragEvent) {

    }

    @Override
    public void initializeNodes() {
        getRoot().setExpanded(false);
        getRoot().getChildren().add(new TreeItem<String>());
    }
}
