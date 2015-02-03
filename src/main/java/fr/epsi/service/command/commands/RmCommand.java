package fr.epsi.service.command.commands;

import fr.epsi.dto.FileDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class RmCommand extends FTPCommand {

    private FileDTO fileDTO;

    public RmCommand(FileDTO fileDTO) {
        super();

        this.fileDTO = fileDTO;
    }

    @Override
    public void execute() throws Exception {
        try {
            PrintWriter out = getSocketPrintWriter();
            out.println("rm" + SEPARATOR + fileDTO.getFile().toPath().getFileName());
            out.flush();

            BufferedReader in = getSocketBufferedReader();
            String responseStatus = in.readLine().split(" : ")[1];

            if (!responseStatus.equals("OK")) {
                executed = true;

                throw new Exception("Wrong response status !");
            }

            super.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
