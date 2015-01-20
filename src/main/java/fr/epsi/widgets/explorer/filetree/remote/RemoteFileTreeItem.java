package fr.epsi.widgets.explorer.filetree.remote;

import fr.epsi.widgets.explorer.filetree.FileTreeItem;

import java.io.File;

public class RemoteFileTreeItem extends FileTreeItem {
    public RemoteFileTreeItem(File file) {
        super(file);
    }

    @Override
    public void generateChildNodes() {
        if (this.getChildren().size() < 1) {
            return;
        }

        this.getChildren().clear();

        for (File subFile : file.listFiles()) {
            FileTreeItem treeNode = new RemoteFileTreeItem(subFile);
            this.getChildren().add(treeNode);
        }
    }
}
