package fr.epsi.service;

import javafx.concurrent.Service;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class FTPService extends Service<Void> {

    protected TextArea console;
    protected Socket socket;

    public FTPService() {}

    public FTPService(Socket socket, TextArea console) {
        this.socket = socket;
        this.console = console;
    }

    protected void disconnect() throws IOException {
        console.appendText("Disconnecting...");

        PrintWriter out = getSocketPrintWriter();
        out.println("exit");
        out.flush();

        socket.close();
    }

    public BufferedReader getSocketBufferedReader() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public PrintWriter getSocketPrintWriter() throws IOException {
        return new PrintWriter(socket.getOutputStream());
    }

    public TextArea getConsole() {
        return console;
    }

    public void setConsole(TextArea console) {
        this.console = console;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
