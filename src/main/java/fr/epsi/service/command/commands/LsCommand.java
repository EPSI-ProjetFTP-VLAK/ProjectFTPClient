package fr.epsi.service.command.commands;

import java.io.*;
import java.net.Socket;

public class LsCommand extends FTPCommand {

    @Override
    public void execute(Socket socket) throws Exception {
        try {
            PrintWriter out = getSocketPrintWriter(socket);
            out.println("ls");
            out.flush();

            BufferedReader stringIn = getSocketBufferedReader(socket);
            String[] socketResponse = stringIn.readLine().split(":");

            int numberOfFiles = Integer.valueOf(socketResponse[1]);
            response = new File[numberOfFiles];
            for (int fileIndex = 0; fileIndex < numberOfFiles; fileIndex++) {
                response[fileIndex] = readFileFromSocket(socket);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        super.execute(socket);
    }

    public File readFileFromSocket(Socket socket)
            throws IOException, ClassNotFoundException {
        ObjectInputStream objectIn = getSocketObjectInputStream(socket);

        return (File) objectIn.readObject();
    }
}
