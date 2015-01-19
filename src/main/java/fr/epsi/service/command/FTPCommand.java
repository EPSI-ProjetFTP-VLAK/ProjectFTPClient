package fr.epsi.service.command;

import java.net.Socket;

public class FTPCommand {

    private boolean executed;

    public FTPCommand() {
        executed = false;
    }

    public void execute(Socket socket) {
        executed = true;
    }

    public boolean isExecuted() {
        return executed;
    }
}
