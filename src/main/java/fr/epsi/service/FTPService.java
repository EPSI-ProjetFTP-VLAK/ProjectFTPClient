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

        if (socket != null && !getSocket().isClosed()) {
            PrintWriter out = getSocketPrintWriter();
            out.println("exit");
            out.flush();

            getSocket().close();
        }
    }

    public BufferedReader getSocketBufferedReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
    }

    public PrintWriter getSocketPrintWriter() throws IOException {
        return new PrintWriter(getSocket().getOutputStream());
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
