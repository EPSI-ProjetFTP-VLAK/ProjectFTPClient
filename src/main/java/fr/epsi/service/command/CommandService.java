package fr.epsi.service.command;

import fr.epsi.service.FTPService;
import fr.epsi.service.command.commands.FTPCommand;
import fr.epsi.service.connection.ConnectionState;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Queue;

public class CommandService extends FTPService {

    private Queue<FTPCommand> commandQueue;

    public CommandService() {
    }

    public CommandService(Socket socket, TextArea console) {
        super(socket, console);
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (!isCancelled()) {
                    executeNextCommand();
                }

                disconnect();
                updateMessage(ConnectionState.DISCONNECTED.toString());

                return null;
            }

            private void executeNextCommand() {
                if (commandQueue.size() > 0) {
                    commandQueue.poll().execute(socket);
                }
            }
        };
    }

    public BufferedReader getSocketBufferedReader() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public PrintWriter getSocketPrintWriter() throws IOException {
        return new PrintWriter(socket.getOutputStream());
    }

    public void setCommandQueue(Queue<FTPCommand> commandQueue) {
        this.commandQueue = commandQueue;
    }

    public Queue<FTPCommand> getCommandQueue() {
        return commandQueue;
    }
}
