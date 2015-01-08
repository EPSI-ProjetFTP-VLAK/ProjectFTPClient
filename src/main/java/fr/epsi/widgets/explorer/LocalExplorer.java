package fr.epsi.widgets.explorer;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class LocalExplorer extends AbstractExplorer {

    public LocalExplorer() {
        super();
    }

    @Override
    public void generateChildNodes() throws Exception {
        super.generateChildNodes();

        File[] rootDirectories = FileSystemView.getFileSystemView().getRoots();
        for (File rootDirectory : rootDirectories) {
            FileTreeItem treeNode = new FileTreeItem(rootDirectory);
            getRoot().getChildren().add(treeNode);
        }
    }
}
