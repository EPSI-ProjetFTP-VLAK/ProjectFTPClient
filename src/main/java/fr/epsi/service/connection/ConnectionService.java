package fr.epsi.service.connection;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionService extends Service<Void> {
    private String host;
    private String username;
    private String password;
    private int port;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ConnectionService() {
    }

    public ConnectionService(String host, String username, String password, int port)
    {
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
    }

    @Override
    protected Task<Void> createTask()
    {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    socket = createSocket();
                    System.out.println(socket.isConnected() ? "Succès de la connexion" : "Échec de la connexion");

                    in = getSocketBufferedReader();
                    System.out.println(in.readLine());

                    out = getSocketPrintWriter();
                    out.println(username + " " + password);
                    out.flush();

                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
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
