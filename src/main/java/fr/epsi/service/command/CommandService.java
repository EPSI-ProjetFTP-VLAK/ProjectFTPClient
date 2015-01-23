package fr.epsi.service.command;

import fr.epsi.service.FTPService;
import fr.epsi.service.command.commands.FTPCommand;
import fr.epsi.service.connection.ConnectionState;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

import java.net.Socket;
import java.util.PriorityQueue;
import java.util.Queue;

public class CommandService extends FTPService {

    private Queue<FTPCommand> commandQueue = new PriorityQueue<>();

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
                    FTPCommand ftpCommand = commandQueue.poll();

                    try {
                        ftpCommand.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {

                }
            }
        };
    }

    public void setCommandQueue(Queue<FTPCommand> commandQueue) {
        this.commandQueue = commandQueue;
    }

    public Queue<FTPCommand> getCommandQueue() {
        return commandQueue;
    }
}
