package fr.epsi.controller.explorer.local;

import fr.epsi.widgets.explorer.LocalExplorer;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalExplorerController {
    public VBox localExplorerContainer;
    public LocalExplorer localExplorer;

    public void initialize() {
        TreeItem<String> rootNode = generateRootNode();
        rootNode.setExpanded(true);

        localExplorer.setRoot(rootNode);

        try {
            localExplorer.generateChildNodes();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
