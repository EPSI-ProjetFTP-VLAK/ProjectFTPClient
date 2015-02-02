package fr.epsi.controller;

import fr.epsi.service.command.CommandService;
import fr.epsi.service.connection.ConnectionService;
import fr.epsi.service.transfer.DownloadService;
import fr.epsi.service.transfer.UploadService;
import fr.epsi.widgets.transfer.TransferQueue;
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
    private static UploadService uploadService;

    @FXML
    private TextArea console;

    @FXML
    private TransferQueue transferQueue;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        executorService = Executors.newFixedThreadPool(4);

        connectionService = new ConnectionService(socket, console);
        connectionService.setExecutor(executorService);

        commandService = new CommandService(socket, console);
        commandService.setExecutor(executorService);
        commandService.start();

        downloadService = new DownloadService(console, transferQueue);
        downloadService.setExecutor(executorService);
        downloadService.start();

        uploadService = new UploadService(console, transferQueue);
        uploadService.setExecutor(executorService);
        uploadService.start();
    }

    public synchronized static ExecutorService getExecutorService() {
        return executorService;
    }

    public synchronized static ConnectionService getConnectionService() {
        return connectionService;
    }

    public static CommandService getCommandService() {
        return commandService;
    }

    public synchronized static DownloadService getDownloadService() {
        return downloadService;
    }

    public synchronized static UploadService getUploadService() {
        return uploadService;
    }
}
