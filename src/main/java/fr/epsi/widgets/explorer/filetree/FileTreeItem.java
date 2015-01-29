package fr.epsi.widgets.explorer.filetree;

import fr.epsi.Main;
import fr.epsi.dto.FileDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

import java.io.File;

public abstract class FileTreeItem extends TreeItem<String> {

    protected FileDTO file;

    public FileTreeItem(FileDTO file) {
        super(file.getName());
        this.file = file;

        setParameters();
        attachExpandedListener();
    }

    public abstract void generateChildNodes();

    protected void setParameters() {
        if(file.isDirectory()) {
            setExpanded(false);
            setGraphic(new ImageView(Main.class.getResource("/images/folder.png").toExternalForm()));
        } else {
            setGraphic(new ImageView(Main.class.getResource("/images/file.png").toExternalForm()));
        }

        if(!file.getFile().toPath().toString().endsWith(File.separator)) {
            String value = file.getName();
            int indexOf = value.lastIndexOf(File.separator);

            if (indexOf > 0) {
                setValue(value.substring(indexOf + 1));
            } else {
                setValue(value);
            }
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

    @Override
    public boolean isLeaf() {
        return !file.isDirectory();
    }

    public FileDTO getFileDTO() {
        return file;
    }
}
