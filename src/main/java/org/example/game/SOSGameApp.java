package org.example.game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class SOSGameApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sos_game.fxml")));
        primaryStage.setTitle("SOS Game");
        primaryStage.setScene(new Scene(root, 350, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}