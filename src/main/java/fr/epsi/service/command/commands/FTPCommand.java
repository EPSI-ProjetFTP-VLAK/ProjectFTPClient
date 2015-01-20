package fr.epsi.service.command.commands;

import java.io.*;
import java.net.Socket;

public abstract class FTPCommand {

    public static String SEPARATOR = "::--::";

    private boolean executed;
    protected Object[] response;

    public FTPCommand() {
        executed = false;
    }

    public void execute(Socket socket) {
        executed = true;
    }

    public boolean isExecuted() {
        return executed;
    }

    public BufferedReader getSocketBufferedReader(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public PrintWriter getSocketPrintWriter(Socket socket) throws IOException {
        return new PrintWriter(socket.getOutputStream());
    }

    public ObjectOutputStream getSocketObjectOutputStream(Socket socket) throws IOException {
        return new ObjectOutputStream(socket.getOutputStream());
    }

    public ObjectInputStream getSocketObjectInputStream(Socket socket) throws IOException {
        return new ObjectInputStream(socket.getInputStream());
    }

    public Object getResponse() {
        return response;
    }
}
