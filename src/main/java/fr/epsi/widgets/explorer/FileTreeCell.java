package fr.epsi.widgets.explorer;

import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.input.*;

public class FileTreeCell extends TreeCell<String> {

    public FileTreeCell() {
        setupDragAndDrop();

        setStyle("-fx-background-color: transparent;");
    }

    private void setupDragAndDrop() {
        setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // TODO Événement du drag

                Dragboard db = startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                content.putString(getText());
                db.setContent(content);

                event.consume();
            }
        });

        setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                // TODO Implémenter l'événement du drop

                event.consume();
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
