package fr.epsi.service.command.commands;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class CdCommand extends LsCommand {

    private String directory;

    public CdCommand(String directory) {
        super();

        this.directory = directory;
    }

    @Override
    public void execute(Socket socket) {
        PrintWriter out = null;

        try {
            out = getSocketPrintWriter(socket);
            out.println("cd" + SEPARATOR + directory);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.execute(socket);
    }
}
