package fr.epsi.controller.explorer.remote;

import fr.epsi.widgets.explorer.RemoteExplorer;
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
