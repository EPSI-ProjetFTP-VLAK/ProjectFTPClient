package fr.epsi.service.command.commands;

import java.io.*;
import java.net.Socket;

public class RmCommand extends FTPCommand {

    private File file;

    public RmCommand(File file) {
        super();

        this.file = file;
    }

    @Override
    public void execute(Socket socket) throws Exception {
        try {
            PrintWriter out = getSocketPrintWriter(socket);
            out.println("rm" + SEPARATOR + file.toString());
            out.flush();

            BufferedReader stringIn = getSocketBufferedReader(socket);
            String[] socketResponse = stringIn.readLine().split(":");

            super.execute(socket);

            if (Integer.valueOf(socketResponse[1]) != 0) {
                throw new Exception("Wrong response code");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
