package fr.epsi.widgets.explorer.filetree.local;

import fr.epsi.widgets.explorer.filetree.FileTreeItem;

import java.io.File;

public class LocalFileTreeItem extends FileTreeItem {
    public LocalFileTreeItem(File file) {
        super(file);
    }

    @Override
    public void generateChildNodes() {
        if (this.getChildren().size() < 1) {
            return;
        }

        this.getChildren().clear();

        for (File subFile : file.listFiles()) {
            FileTreeItem treeNode = new LocalFileTreeItem(subFile);
            this.getChildren().add(treeNode);
        }
    }
}
