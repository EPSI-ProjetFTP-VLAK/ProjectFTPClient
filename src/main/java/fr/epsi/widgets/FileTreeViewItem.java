package fr.epsi.widgets;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;

import java.io.File;

public class FileTreeViewItem extends TreeItem<String> {
    private File file;

    public FileTreeViewItem(File file) {
        super(file.toString());
        this.file = file;

        setParameters();
        attachExpandedListener();
    }

    private void setParameters() {
        if(this.file.isDirectory()) {
            setExpanded(false);
            //TODO ajouter image setGraphic(new ImageView(folderCollapseImage));
        } else {
            //TODO ajouter image setGraphic(new ImageView(fileImage));
        }

        if(!this.file.toPath().toString().endsWith(File.separator)) {
            String value = file.toString();
            int indexOf = value.lastIndexOf(File.separator);

            if (indexOf > 0) {
                setValue(value.substring(indexOf + 1));
            } else {
                setValue(value);
            }
        }

        if (this.file.listFiles() != null) {
            this.getChildren().add(new TreeItem<String>());
        }
    }

    private void attachExpandedListener() {
        this.expandedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                prepareChildNodes();
            }
        });
    }

    public void prepareChildNodes() {
        if (this.getChildren().size() < 1) {
            return;
        }

        this.getChildren().clear();

        for (File subFile : this.file.listFiles()) {
            FileTreeViewItem treeNode = new FileTreeViewItem(subFile);
            this.getChildren().add(treeNode);
        }
    }
}
