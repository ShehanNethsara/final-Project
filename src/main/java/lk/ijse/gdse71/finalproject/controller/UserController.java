package lk.ijse.gdse71.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class UserController {

    @FXML
    private Button buttsign;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    private AnchorPane userpage;

    @FXML
    void signInOnAction(ActionEvent event) throws IOException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.equals("shehan") && password.equals("1234")){
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            userpage.getChildren().clear();
            userpage.getChildren().add(load);
        }else {
            new Alert(Alert.AlertType.ERROR,"something wrong !").show();
        }
    }

}
