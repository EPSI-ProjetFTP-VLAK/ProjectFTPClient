package fr.epsi.controller.connection;

import fr.epsi.service.connection.ConnectionService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ConnectionController
{
    public TextField host;
    public TextField username;
    public PasswordField password;
    public TextField port;
    public Button connect;

    public void connect(ActionEvent actionEvent)
    {
        String host = this.host.getText();
        String username = this.username.getText();
        String password = this.password.getText();
        int port = Integer.valueOf(this.port.getText());

        ConnectionService connectionService = new ConnectionService(host, username, password, port);
        connectionService.start();
    }
}
