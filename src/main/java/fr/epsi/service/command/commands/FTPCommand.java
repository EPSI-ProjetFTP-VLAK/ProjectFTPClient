package fr.epsi.service.command.commands;

import fr.epsi.controller.MainController;

import java.io.*;

public abstract class FTPCommand {

    public static String SEPARATOR = "::--::";

    protected boolean executed;
    protected Object[] response;

    public FTPCommand() {
        executed = false;
    }

    public void execute() throws Exception {
        executed = true;
    }

    public boolean isExecuted() {
        return executed;
    }

    public BufferedReader getSocketBufferedReader() throws IOException {
        return new BufferedReader(new InputStreamReader(MainController.getConnectionService().getSocket().getInputStream()));
    }

    public PrintWriter getSocketPrintWriter() throws IOException {
        return new PrintWriter(MainController.getConnectionService().getSocket().getOutputStream());
    }

    public ObjectOutputStream getSocketObjectOutputStream() throws IOException {
        return new ObjectOutputStream(MainController.getConnectionService().getSocket().getOutputStream());
    }

    public ObjectInputStream getSocketObjectInputStream() throws IOException {
        return new ObjectInputStream(MainController.getConnectionService().getSocket().getInputStream());
    }

    public Object getResponse() {
        return response;
    }
}
