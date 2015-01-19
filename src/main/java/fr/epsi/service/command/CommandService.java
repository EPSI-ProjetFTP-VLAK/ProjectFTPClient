package fr.epsi.service.command;

import fr.epsi.service.FTPService;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CommandService extends FTPService {
    private Socket socket;
    private TextArea console;

    public CommandService(Socket socket, TextArea console) {
        super(socket, console);
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                while (!isCancelled()) {}

                console.appendText("Disconnecting...");

//                out.println("exit");
//                out.flush();

                socket.close();

                return null;
            }
        };
    }

    public BufferedReader getSocketBufferedReader() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public PrintWriter getSocketPrintWriter() throws IOException {
        return new PrintWriter(socket.getOutputStream());
    }
}
