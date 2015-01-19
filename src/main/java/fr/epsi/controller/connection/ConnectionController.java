package fr.epsi.controller.connection;

import fr.epsi.controller.MainController;
import fr.epsi.service.connection.ConnectionService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ConnectionController
{
    @FXML
    private TextField host;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private TextField port;

    @FXML
    private Button connectionButton;

    private ConnectionService connectionService;

    public void connect(ActionEvent actionEvent)
    {
        String host = this.host.getText();
        String username = this.username.getText();
        String password = this.password.getText();
        int port = Integer.valueOf(this.port.getText());

        connectionService = MainController.getConnectionService();
        connectionService.setHost(host);
        connectionService.setUsername(username);
        connectionService.setPassword(password);
        connectionService.setPort(port);

        connectionService.start();

        connectionButton.setText("Déconnexion");
        connectionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                disconnect(actionEvent);
            }
        });
    }

    public void disconnect(ActionEvent actionEvent) {
        connectionService.cancel();

        connectionButton.setText("Connection");
        connectionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                connect(actionEvent);
            }
        });
    }

    private void appendToConsole(String text) {
        ((TextArea) connectionButton.getScene().lookup("#console")).appendText(text);
    }
}
