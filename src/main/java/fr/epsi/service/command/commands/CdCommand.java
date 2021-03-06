package fr.epsi.service.command.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class CdCommand extends LsCommand {

    private String directory;

    public CdCommand(String directory) {
        super();

        this.directory = directory;
    }

    @Override
    public void execute() throws Exception {
        PrintWriter out = null;

        try {
            out = getSocketPrintWriter();
            out.println("cd" + SEPARATOR + directory);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader stringIn = getSocketBufferedReader();
        stringIn.readLine();
//        MainController.getConnectionService().getConsole().appendText("Currently at : " + stringIn.readLine() + "...");

        super.execute();
    }
}
