package lk.ijse.gdse71.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public Button btnlogout;
    @FXML
    private AnchorPane main;

    @FXML
    private Button conbuttn;

    @FXML
    private Button cusbuttn;

    @FXML
    private Button empobuttn;

    @FXML
    private Button invenbutton;

    @FXML
    private Button orderbuttn;

    @FXML
    private Button paydetalbuttn;

    @FXML
    private Button paymebuttn;

    @FXML
    private Button prodbuttn;

    @FXML
    private Button prodetailbuttn;

    @FXML
    private Button supbottn;

    @FXML
    private Button supdetabuttn;

    @FXML
    void navigateToContainPage(ActionEvent event) {
            navigateTo("/view/ContainPage.fxml");
    }

    @FXML
    void navigateToCustomerPage(ActionEvent event) {
            navigateTo("/view/Customer.fxml");
    }

    @FXML
    void navigateToEmployeePage(ActionEvent event) {
            navigateTo("/view/Employee.fxml");
    }

    @FXML
    void navigateToInventoryPage(ActionEvent event) {
            navigateTo("/view/Inventary.fxml");
    }

    @FXML
    void navigateToOrderPage(ActionEvent event) {
            navigateTo("/view/Order.fxml");
    }

    @FXML
    void navigateToPaydetailsPage(ActionEvent event) {
            navigateTo("/view/PayDetails.fxml");
    }

    @FXML
    void navigateToPaymentPage(ActionEvent event) {
            navigateTo("/view/Payment.fxml");
    }

    @FXML
    void navigateToProductDetailsPage(ActionEvent event) {
            navigateTo("/view/ProductDetails.fxml");
    }

    @FXML
    void navigateToProductPage(ActionEvent event) {
            navigateTo("/view/Product.fxml");
    }

    @FXML
    void navigateToSupplierDetailsPage(ActionEvent event) {
             navigateTo("/view/SupplierDetails.fxml");
    }

    @FXML
    void navigateToSupplierPage(ActionEvent event) {
              navigateTo("/view/Supplier.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
                 //   navigateTo("/view/Customer.fxml");
    }
    @FXML
    public void ClickonActionlogoutbtn(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/User.fxml"));
                AnchorPane root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnlogout.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"loading loging page");
        }
    }

        public void navigateTo(String fxmlPath) {
            try {
                main.getChildren().clear();
                AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
                main.getChildren().add(load);

            } catch (IOException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
            }

        }

}
