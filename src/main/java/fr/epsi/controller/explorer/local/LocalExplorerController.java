package fr.epsi.controller.explorer.local;

import fr.epsi.widgets.FileTreeCell;
import fr.epsi.widgets.FileTreeItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalExplorerController {
    public VBox localExplorerContainer;
    public TreeView<String> localExplorer;

    public void initialize() {
        TreeItem<String> rootNode = generateRootNode();
        rootNode.setExpanded(true);

        generateChildNodes(rootNode);

        localExplorer.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> p) {
                return new FileTreeCell();
            }
        });
        localExplorer.setRoot(rootNode);
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
