package fr.epsi.service.connection;

import fr.epsi.service.FTPService;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

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
        SocketAddress socketAddress = new InetSocketAddress(host, port);
        Socket socket = new Socket();
        socket.connect(socketAddress, 10000);

        return socket;
    }

    public String getHost() {
        return host;
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
