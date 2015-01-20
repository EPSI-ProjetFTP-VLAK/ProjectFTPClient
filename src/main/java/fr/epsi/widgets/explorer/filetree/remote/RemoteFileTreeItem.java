package fr.epsi.widgets.explorer.filetree.remote;

import fr.epsi.widgets.explorer.filetree.FileTreeItem;

import java.io.File;

public class RemoteFileTreeItem extends FileTreeItem {

    public RemoteFileTreeItem(File file) {
        super(file);
    }

    @Override
    public void generateChildNodes() {

    }
}
