package fr.epsi.service.connection;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionService extends Service<Void>
{
    private String host;
    private String username;
    private String password;
    private int port;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

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
                    socket = new Socket(host, port);

                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    System.out.println(in.readLine());

                    out = new PrintWriter(socket.getOutputStream());
                    out.println(username + " : " + password);
                    out.flush();

                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        };
    }
}
