package fr.epsi.widgets.explorer.filetree;

import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.input.*;

public abstract class FileTreeCell extends TreeCell<String> {

    public FileTreeCell() {
        setupDragAndDrop();

        setStyle("-fx-background-color: transparent;");
    }

    protected abstract void doOnDrag(MouseEvent mouseEvent);
    protected abstract void doOnDrop(DragEvent dragEvent);

    private void setupDragAndDrop() {
        setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                doOnDrag(mouseEvent);

                Dragboard db = startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                content.putString(getText());
                db.setContent(content);

                mouseEvent.consume();
            }
        });

        setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent dragEvent) {
                doOnDrop(dragEvent);

                dragEvent.consume();
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
