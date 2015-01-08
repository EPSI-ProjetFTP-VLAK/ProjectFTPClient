package fr.epsi.widgets;

import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.input.MouseEvent;

public class FileTreeCell extends TreeCell<String> {

    public FileTreeCell() {
        setOnDragDetected(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

            }
        });
    }

    @Override
    protected void updateItem(String filename, boolean empty) {
        super.updateItem(filename, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(getItem() == null ? "" : getItem());
            setGraphic(getTreeItem().getGraphic());
        }
    }
}
