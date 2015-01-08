package fr.epsi.controller.explorer.remote;

import fr.epsi.widgets.explorer.RemoteExplorer;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;

public class RemoteExplorerController {
    public VBox remoteExplorerContainer;
    public RemoteExplorer remoteExplorer;

    public void initialize() {
        TreeItem<String> rootNode = generateRootNode();
        rootNode.setExpanded(true);

        remoteExplorer.setRoot(rootNode);

        try {
            remoteExplorer.generateChildNodes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TreeItem<String> generateRootNode() {
        return new TreeItem<String>("Serveur-FTP");
    }
}
