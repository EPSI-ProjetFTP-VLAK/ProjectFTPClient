package fr.epsi.widgets.explorer.filetree;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;

import java.io.File;

public abstract class FileTreeItem extends TreeItem<String> {
    protected File file;

    public FileTreeItem(File file) {
        super(file.toString());
        this.file = file;

        setParameters();
        attachExpandedListener();
    }

    public abstract void generateChildNodes();

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
                generateChildNodes();
            }
        });
    }
}
