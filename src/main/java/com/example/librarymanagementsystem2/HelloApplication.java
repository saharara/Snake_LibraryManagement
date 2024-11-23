package com.example.librarymanagementsystem2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Start.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        
        stage.setTitle("Library Management System");
        //stage.getIcons().add(new Image(HelloApplication.class.getResourceAsStream("src/main/resources/com/example/librarymanagementsystem2/pictureStyle/logo.png")));
        stage.setMinHeight(450); // kích thước tôi thiểu
        stage.setMinWidth(615);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}