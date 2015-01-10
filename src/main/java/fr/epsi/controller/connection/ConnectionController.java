package fr.epsi.controller.connection;

import fr.epsi.controller.MainController;
import fr.epsi.service.connection.ConnectionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
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
    private Button connect;

    public void connect(ActionEvent actionEvent)
    {
        String host = this.host.getText();
        String username = this.username.getText();
        String password = this.password.getText();
        int port = Integer.valueOf(this.port.getText());

        ConnectionService connectionService = MainController.getConnectionService();
        connectionService.setHost(host);
        connectionService.setUsername(username);
        connectionService.setPassword(password);
        connectionService.setPort(port);

        connectionService.start();
    }
}
