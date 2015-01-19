package fr.epsi.controller;

import fr.epsi.service.connection.ConnectionService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private static ConnectionService connectionService;

    @FXML
    private TextArea console;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connectionService = new ConnectionService(console);
    }

    public static ConnectionService getConnectionService() {
        return connectionService;
    }

    public TextArea getConsole() {
        return console;
    }

    public void setConsole(TextArea console) {
        this.console = console;
    }
}
