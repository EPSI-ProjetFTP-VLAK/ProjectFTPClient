package fr.epsi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/main.fxml"));

        primaryStage.setTitle("FTP Client");

        Scene mainScene = new Scene(root, 800, 800);
        mainScene.getStylesheets().add(getClass().getClassLoader().getResource("stylesheet/main.css").toExternalForm());

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
