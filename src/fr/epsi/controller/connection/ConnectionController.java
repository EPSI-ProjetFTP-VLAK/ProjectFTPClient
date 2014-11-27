package fr.epsi.controller.connection;

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
        String port = this.port.getText();

        System.out.println(host + "; " + username + "; " + password + "; " + port);
    }
}
