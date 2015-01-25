package fr.epsi.controller;

import fr.epsi.service.command.CommandService;
import fr.epsi.service.connection.ConnectionService;
import fr.epsi.service.transfer.DownloadService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainController implements Initializable {
    private Socket socket;
    private static ExecutorService executorService;
    private static ConnectionService connectionService;
    private static CommandService commandService;
    private static DownloadService downloadService;

    @FXML
    private TextArea console;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        executorService = Executors.newFixedThreadPool(3);

        connectionService = new ConnectionService(socket, console);
        connectionService.setExecutor(executorService);

        commandService = new CommandService(socket, console);
        commandService.setExecutor(executorService);
        commandService.start();

        downloadService = new DownloadService(socket, console);
        downloadService.setExecutor(executorService);
        downloadService.start();
    }

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    public static ConnectionService getConnectionService() {
        return connectionService;
    }

    public static CommandService getCommandService() {
        return commandService;
    }

    public static DownloadService getDownloadService() {
        return downloadService;
    }
}
