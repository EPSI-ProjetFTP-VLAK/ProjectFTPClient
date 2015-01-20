package fr.epsi.widgets.explorer;

import com.sun.javafx.scene.control.skin.TreeCellSkin;
import fr.epsi.widgets.explorer.filetree.FileTreeCell;
import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;

public abstract class AbstractExplorer extends TreeView<String> {

    public AbstractExplorer() {
        assignCellFactory();
        setupDragAndDrop();
    }

    public abstract void doOnFileDrag(MouseEvent mouseEvent);
    public abstract void doOnFileDrop(DragEvent dragEvent);
    public abstract void initializeNodes();

    private void assignCellFactory() {
        setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> p) {
                return new FileTreeCell() {
                    @Override
                    protected void doOnDrag(MouseEvent mouseEvent) {
                        doOnFileDrag(mouseEvent);
                    }

                    @Override
                    protected void doOnDrop(DragEvent dragEvent) {
                        doOnFileDrop(dragEvent);
                    }
                };
            }
        });
    }

    private void setupDragAndDrop() {
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
            @Override public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();

                if (event.getTarget() instanceof TreeCellSkin) {
                    FileTreeCell fileTreeCell = (FileTreeCell) ((TreeCellSkin) event.getTarget()).getParent();

                    fileTreeCell.getTreeView().getStyleClass().remove("hovered");
                }

                event.setDropCompleted(true);
                event.consume();
            }
        });
    }

    private boolean isSourceDifferentToTarget(DragEvent event) {
        return !((FileTreeCell) event.getGestureSource()).getTreeView().getId().equals(getId());
    }
}
