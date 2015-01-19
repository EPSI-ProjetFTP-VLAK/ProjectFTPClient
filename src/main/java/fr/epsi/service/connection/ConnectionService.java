package fr.epsi.service.connection;

import fr.epsi.controller.connection.ConnectionState;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionService extends Service<Void> {
    private TextArea console;

    private String host;
    private String username;
    private String password;
    private int port;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ConnectionService(TextArea console) {
        this.console = console;
    }

    public ConnectionService(String host, String username, String password, int port, TextArea console)
    {
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
        this.console = console;
    }

    @Override
    protected Task<Void> createTask()
    {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                connect();
                authenticate();

                while (!isCancelled()) {}

                disconnect();

                return null;
            }

            private void connect() throws IOException {
                console.appendText("Initializing connection...\n");

                socket = createSocket();
                console.appendText(socket.isConnected() ? "Connection successful !\n" : "Failed to connect to server !\n");

                updateMessage(ConnectionState.CONNECTED.toString());
            }

            private void authenticate() throws IOException {
                in = getSocketBufferedReader();
                System.out.println(in.readLine());

                out = getSocketPrintWriter();
                out.println(username + " " + password);
                out.flush();

                updateMessage(ConnectionState.AUTHENTICATED.toString());
            }

            private void disconnect() {
                console.appendText("Disconnecting...");

                out.println("exit");
                out.flush();

                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                updateMessage(ConnectionState.DISCONNECTED.toString());
            }
        };
    }

    public Socket createSocket() throws IOException {
        return new Socket(host, port);
    }

    public BufferedReader getSocketBufferedReader() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public PrintWriter getSocketPrintWriter() throws IOException {
        return new PrintWriter(socket.getOutputStream());
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public TextArea getConsole() {
        return console;
    }

    public void setConsole(TextArea console) {
        this.console = console;
    }
}
