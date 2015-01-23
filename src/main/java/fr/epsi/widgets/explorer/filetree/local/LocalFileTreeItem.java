package fr.epsi.widgets.explorer.filetree.local;

import fr.epsi.dto.FileDTO;
import fr.epsi.widgets.explorer.filetree.FileTreeItem;

import java.io.File;

public class LocalFileTreeItem extends FileTreeItem {

    public LocalFileTreeItem(FileDTO file) {
        super(file);
    }

    @Override
    public void generateChildNodes() {
        if (isLeaf()) {
            return;
        }

        this.getChildren().clear();

        try {
            for (File subFile : file.getFile().listFiles()) {
                FileTreeItem treeNode = new LocalFileTreeItem(new FileDTO(subFile));
                this.getChildren().add(treeNode);
            }
        } catch (NullPointerException e) {

        }
    }
}
