package fr.epsi;

import fr.epsi.controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/main.fxml"));

        primaryStage.setTitle("FTP Client");

        Scene mainScene = new Scene(root, 800, 800);
        mainScene.getStylesheets().add(getClass().getClassLoader().getResource("stylesheet/main.css").toExternalForm());

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                MainController.getDownloadService().cancel();
                MainController.getCommandService().cancel();
                MainController.getConnectionService().cancel();
                MainController.getUploadService().cancel();

                MainController.getExecutorService().shutdown();

                Platform.exit();
            }
        });

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
