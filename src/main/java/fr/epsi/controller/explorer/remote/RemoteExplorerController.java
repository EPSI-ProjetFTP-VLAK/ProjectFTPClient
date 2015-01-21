package fr.epsi.controller.explorer.remote;

import fr.epsi.widgets.explorer.RemoteExplorer;
import fr.epsi.widgets.explorer.filetree.remote.RemoteRootFileTreeItem;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RemoteExplorerController implements Initializable {
    public VBox remoteExplorerContainer;
    public RemoteExplorer remoteExplorer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeItem<String> rootNode = generateRootNode();
        rootNode.setExpanded(false);

        remoteExplorer.setRoot(rootNode);
        remoteExplorer.initializeNodes();
    }

    public TreeItem<String> generateRootNode() {
        return new RemoteRootFileTreeItem("Serveur-FTP");
    }
}
