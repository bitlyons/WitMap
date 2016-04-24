package xyz.lyonzy.map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/map.fxml"));
        primaryStage.setTitle("Waterford Institute of Technology Map");
        primaryStage.setScene(new Scene(root, 1025, 700));
        primaryStage.setResizable(false);
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}