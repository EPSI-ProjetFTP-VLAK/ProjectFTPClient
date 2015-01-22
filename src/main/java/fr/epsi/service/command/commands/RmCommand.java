package fr.epsi.service.command.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class RmCommand extends FTPCommand {

    private File file;

    public RmCommand(File file) {
        super();

        this.file = file;
    }

    @Override
    public void execute() throws Exception {
        try {
            PrintWriter out = getSocketPrintWriter();
            out.println("rm" + SEPARATOR + file.toString());
            out.flush();

            BufferedReader stringIn = getSocketBufferedReader();
            String[] socketResponse = stringIn.readLine().split(":");

            super.execute();

            if (Integer.valueOf(socketResponse[1]) != 0) {
                throw new Exception("Wrong response code");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
