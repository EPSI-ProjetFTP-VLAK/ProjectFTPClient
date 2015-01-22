package fr.epsi.controller;

import fr.epsi.service.command.CommandService;
import fr.epsi.service.connection.ConnectionService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private Socket socket;
    private static ConnectionService connectionService;
    private static CommandService commandService;

    @FXML
    private TextArea console;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connectionService = new ConnectionService(socket, console);

        commandService = new CommandService(socket, console);
        commandService.start();
    }

    public static ConnectionService getConnectionService() {
        return connectionService;
    }

    public static CommandService getCommandService() {
        return commandService;
    }
}
