package fr.epsi.widgets.explorer.filetree.local;

import fr.epsi.dto.FileDTO;
import fr.epsi.widgets.explorer.filetree.FileTreeItem;

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
            for (FileDTO subFile : file.getChildren()) {
                FileTreeItem treeNode = new LocalFileTreeItem(subFile);
                this.getChildren().add(treeNode);
            }
        } catch (NullPointerException e) {

        }
    }
}
