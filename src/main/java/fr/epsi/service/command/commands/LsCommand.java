package fr.epsi.service.command.commands;

import fr.epsi.dto.FileDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

public class LsCommand extends FTPCommand {

    @Override
    public void execute() throws Exception {
        try {
            PrintWriter out = getSocketPrintWriter();
            out.println("ls" + SEPARATOR);
            out.flush();

            BufferedReader stringIn = getSocketBufferedReader();
            String[] socketResponse = stringIn.readLine().split(":");

            int numberOfFiles = Integer.valueOf(socketResponse[1]);
            response = new FileDTO[numberOfFiles];
            for (int fileIndex = 0; fileIndex < numberOfFiles; fileIndex++) {
                response[fileIndex] = readFileFromSocket();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        super.execute();
    }

    public FileDTO readFileFromSocket() throws IOException, ClassNotFoundException {
        ObjectInputStream objectIn = getSocketObjectInputStream();

        return (FileDTO) objectIn.readObject();
    }
}
