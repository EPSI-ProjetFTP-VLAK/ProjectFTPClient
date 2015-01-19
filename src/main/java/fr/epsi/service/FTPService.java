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

    public FTPService(Socket socket, TextArea console) {
        this.socket = socket;
        this.console = console;
    }

    public BufferedReader getSocketBufferedReader() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public PrintWriter getSocketPrintWriter() throws IOException {
        return new PrintWriter(socket.getOutputStream());
    }
}
