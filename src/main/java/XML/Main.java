package main.java.XML;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ui/ui.fxml"));
        primaryStage.setTitle("Storage");
        primaryStage.setScene(new Scene(root, 800, 650));
        primaryStage.show();
    }
}
