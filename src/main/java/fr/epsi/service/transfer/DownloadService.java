package fr.epsi.service.transfer;

import fr.epsi.controller.MainController;
import fr.epsi.service.FTPService;
import fr.epsi.service.connection.ConnectionState;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DownloadService extends FTPService {
    private BufferedReader in;
    private PrintWriter out;

    public DownloadService() {}

    public DownloadService(Socket socket, TextArea console) {
        super(socket, console);
    }

    @Override
    protected Task<Void> createTask()
    {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    while (!isCancelled()) {}

                    disconnect();
                    updateMessage(ConnectionState.DISCONNECTED.toString());
                } catch (Exception e) {
                    console.appendText(e.getMessage() + " !");
                    e.printStackTrace();
                }

                return null;
            }
        };
    }

    public BufferedReader getSocketBufferedReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
    }

    public PrintWriter getSocketPrintWriter() throws IOException {
        return new PrintWriter(getSocket().getOutputStream());
    }

    @Override
    public Socket getSocket() {
        if (socket == null) {
            socket = createSocket();
        }

        return socket;
    }

    public Socket createSocket() {
        Socket socket = null;
        try {
            socket = new Socket(MainController.getConnectionService().getHost(), MainController.getConnectionService().getPort() + 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return socket;
    }
}
