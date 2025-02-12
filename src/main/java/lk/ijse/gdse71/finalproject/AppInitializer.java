package lk.ijse.gdse71.finalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/User.fxml"));
        Scene scene = new Scene(load);
        stage.setTitle("Login");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/ccc.jpg")));
        stage.setScene(scene);
        stage.show();
    }


}