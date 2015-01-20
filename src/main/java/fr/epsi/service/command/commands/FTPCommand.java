package fr.epsi.service.command.commands;

import java.net.Socket;

public abstract class FTPCommand {

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
