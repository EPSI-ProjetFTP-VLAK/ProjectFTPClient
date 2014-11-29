package fr.epsi.widgets;

import javafx.scene.control.TreeItem;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileTreeViewItem extends TreeItem<String> {
    private String fullPath;
    private boolean isDirectory;

    public FileTreeViewItem(Path file)
    {
        super(file.toString());
        this.fullPath = file.toString();

        setParameters(file);
    }

    private void setParameters(Path file) {
        if(Files.isDirectory(file)){
            isDirectory = true;
            //TODO ajouter image setGraphic(new ImageView(folderCollapseImage));
        }else{
            isDirectory=false;
            //TODO ajouter image setGraphic(new ImageView(fileImage));
        }

        if(!fullPath.endsWith(File.separator)){
            String value=file.toString();
            int indexOf=value.lastIndexOf(File.separator);

            if (indexOf>0) {
                setValue(value.substring(indexOf+1));
            } else {
                setValue(value);
            }
        }
    }
}
