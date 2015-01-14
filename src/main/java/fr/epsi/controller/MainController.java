package fr.epsi.controller;

import fr.epsi.service.connection.ConnectionService;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class MainController
{
    private static ConnectionService connectionService;

    @FXML
    private TextArea console;

    public MainController() {
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
