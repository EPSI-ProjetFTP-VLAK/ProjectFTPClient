package fr.epsi.controller.explorer.remote;

import fr.epsi.widgets.FileTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class RemoteExplorerController {
    public VBox remoteExplorerContainer;
    public TreeView<String> remoteExplorer;

    public void initialize() {
        TreeItem<String> rootNode = generateRootNode();
        rootNode.setExpanded(true);

        generateChildNodes(rootNode);

        remoteExplorer.setRoot(rootNode);
    }

    public TreeItem<String> generateRootNode() {
        String root = "";
        try {
            root = getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return new TreeItem<String>(root);
    }

    public String getHostName() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }

    public void generateChildNodes(TreeItem<String> rootNode) {
        File[] rootDirectories = FileSystemView.getFileSystemView().getRoots();
        for (File rootDirectory : rootDirectories) {
            FileTreeItem treeNode = new FileTreeItem(rootDirectory);
            rootNode.getChildren().add(treeNode);
        }
    }
}
