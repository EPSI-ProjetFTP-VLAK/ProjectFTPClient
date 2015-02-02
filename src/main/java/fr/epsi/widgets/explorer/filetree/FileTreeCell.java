package fr.epsi.widgets.explorer.filetree;

import com.sun.javafx.scene.control.skin.TreeCellSkin;
import fr.epsi.dto.FileDTO;
import fr.epsi.widgets.explorer.filetree.remote.RemoteFileTreeItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.input.*;

public abstract class FileTreeCell extends TreeCell<String> {

    public FileTreeCell() {
        setupDragAndDrop();
        setupContextMenu();

        setStyle("-fx-background-color: transparent;");
    }

    protected abstract void doOnDrag(MouseEvent mouseEvent);
    protected abstract void doOnDrop(FileTreeItem sourceFileTreeItem, FileTreeItem targetFileTreeItem, DragEvent dragEvent);
    protected abstract void doOnDelete(FileDTO fileDTO);

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
                dragEvent.consume();
            }
        });

        setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (isSourceDifferentToTarget(event)) {
                    getStyleClass().add("hovered");
                }

                event.consume();
            }
        });

        setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (isSourceDifferentToTarget(event)) {
                    event.acceptTransferModes(TransferMode.ANY);
                }

                event.consume();
            }
        });

        setOnDragDropped(new EventHandler<DragEvent>() {
            @Override public void handle(DragEvent dragEvent) {
                Dragboard db = dragEvent.getDragboard();

                /*if ((((Node) dragEvent.getTarget()).getParent() instanceof TreeCellSkin)
                        && (dragEvent.getSource() instanceof FileTreeCell)) {*/
                    FileTreeCell targetFileTreeCell = (FileTreeCell) ((Node) dragEvent.getTarget()).getParent().getParent();
                    FileTreeItem targetFileTreeItem = (FileTreeItem) targetFileTreeCell.getTreeView().getTreeItem(targetFileTreeCell.getIndex());

                    FileTreeCell sourceFileTreeCell = ((FileTreeCell) dragEvent.getGestureSource());
                    FileTreeItem sourceFileTreeItem = (FileTreeItem) sourceFileTreeCell.getTreeView().getTreeItem(sourceFileTreeCell.getIndex());

                    if (targetFileTreeItem.isLeaf()) {
                        return;
                    }

                    targetFileTreeCell.getStyleClass().remove("hovered");

                    doOnDrop(sourceFileTreeItem, targetFileTreeItem, dragEvent);
               // }

                dragEvent.setDropCompleted(true);
                dragEvent.consume();
            }
        });
    }

    private boolean isSourceDifferentToTarget(DragEvent event) {
        return !event.getGestureSource().equals(this);
    }

    private void setupContextMenu() {
        MenuItem deleteMenuItem = new MenuItem("Supprimer");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                doOnDelete(((RemoteFileTreeItem) getTreeView().getSelectionModel().getSelectedItem()).getFileDTO());
            }
        });

        setContextMenu(new ContextMenu(deleteMenuItem));
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
