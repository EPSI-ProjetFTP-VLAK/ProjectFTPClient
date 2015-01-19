package fr.epsi.service.connection;

import fr.epsi.service.FTPService;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionService extends FTPService {
    private String host;
    private String username;
    private String password;
    private int port;

    private BufferedReader in;
    private PrintWriter out;

    public ConnectionService() {}

    public ConnectionService(Socket socket, TextArea console) {
        super(socket, console);
    }

    @Override
    protected Task<Void> createTask()
    {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    connect();
                    authenticate();

                    while (!isCancelled()) {}

                    disconnect();
                    updateMessage(ConnectionState.DISCONNECTED.toString());
                } catch (Exception e) {
                    console.appendText(e.getMessage() + " !");
                    e.printStackTrace();
                }

                return null;
            }

            private void connect() throws Exception {
                console.appendText("Initializing connection...\n");

                socket = createSocket();
                console.appendText(socket.isConnected() ? "Connection successful !\n" : "Failed to connect to server !\n");

                updateMessage(ConnectionState.CONNECTED.toString());
            }

            private void authenticate() throws Exception {
                in = getSocketBufferedReader();
                console.appendText(in.readLine() + "\n");

                out = getSocketPrintWriter();
                out.println(username + " " + password);
                out.flush();

                String authResponse = in.readLine();
                console.appendText(authResponse + "\n");

                if (!authResponse.equals("AUTH : OK")) {
                    cancel();
                    throw new Exception("Failed to authenticate");
                }

                updateMessage(ConnectionState.AUTHENTICATED.toString());
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
}
