package fr.epsi.widgets.explorer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;

import java.io.File;

public class FileTreeItem extends TreeItem<String> {
    private File file;

    public FileTreeItem(File file) {
        super(file.toString());
        this.file = file;

        setParameters();
        attachExpandedListener();
    }

    private void setParameters() {
        if(file.isDirectory()) {
            setExpanded(false);
            //TODO ajouter image setGraphic(new ImageView(folderCollapseImage));
        } else {
            //TODO ajouter image setGraphic(new ImageView(fileImage));
        }

        if(!file.toPath().toString().endsWith(File.separator)) {
            String value = file.toString();
            int indexOf = value.lastIndexOf(File.separator);

            if (indexOf > 0) {
                setValue(value.substring(indexOf + 1));
            } else {
                setValue(value);
            }
        }

        if (file.listFiles() != null) {
            getChildren().add(new TreeItem<String>());
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
            FileTreeItem treeNode = new FileTreeItem(subFile);
            this.getChildren().add(treeNode);
        }
    }
}
