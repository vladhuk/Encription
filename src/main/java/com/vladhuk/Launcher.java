package com.vladhuk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Locale.setDefault(new Locale("ru", "RU"));
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/Launcher.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        primaryStage.setTitle("Encryption");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
