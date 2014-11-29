package fr.epsi.controller.explorer.local;

import fr.epsi.widgets.FileTreeViewItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class LocalExplorerController {
    public VBox localExplorerContainer;
    public TreeView<String> localExplorer;

    public void initialize() {
        String root = "";
        try {
            root = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        TreeItem<String> rootNode = new TreeItem<String>(root);

        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();
        for (Path rootDirectory : rootDirectories) {
            FileTreeViewItem treeNode = new FileTreeViewItem(rootDirectory);
            rootNode.getChildren().add(treeNode);
        }

        rootNode.setExpanded(true);

        localExplorer.setRoot(rootNode);
    }
}
