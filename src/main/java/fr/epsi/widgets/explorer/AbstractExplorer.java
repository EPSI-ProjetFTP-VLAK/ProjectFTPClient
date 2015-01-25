package fr.epsi.widgets.explorer;

import fr.epsi.dto.FileDTO;
import fr.epsi.widgets.explorer.filetree.FileTreeCell;
import fr.epsi.widgets.explorer.filetree.FileTreeItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public abstract class AbstractExplorer extends TreeView<String> {

    protected FileDTO draggedFile;

    public AbstractExplorer() {
        assignCellFactory();
        setupDragAndDrop();
    }

    public abstract void doOnFileDrag(MouseEvent mouseEvent);
    public abstract void doOnFileDrop(FileTreeItem sourceFileTreeItem, FileTreeItem targetFileTreeItem, DragEvent dragEvent);
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
                    protected void doOnDrop(FileTreeItem sourceFileTreeItem, FileTreeItem targetFileTreeItem, DragEvent dragEvent) {
                        doOnFileDrop(sourceFileTreeItem, targetFileTreeItem, dragEvent);
                    }
                };
            }
        });
    }

    private void setupDragAndDrop() {
        getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observableValue, TreeItem<String> stringTreeItem, TreeItem<String> treeItem) {
                if (treeItem instanceof FileTreeItem) {
                    draggedFile = ((FileTreeItem) treeItem).getFileDTO();
                }
            }
        });
    }
}
