package lk.ijse.gdse71.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse71.finalproject.dto.ProductDto;
import lk.ijse.gdse71.finalproject.dto.tm.ProductTm;
import lk.ijse.gdse71.finalproject.dao.custom.Impl.ProductDAOImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


public class ProductController implements Initializable {

    @FXML
    public TableView tblproo;
    @FXML
    public Label lblpid;
    @FXML
    private Button btnpDelete;

    @FXML
    private Button btnpReset;

    @FXML
    private Button btnpSave;

    @FXML
    private Button btnpUpdate;

    @FXML
    private TableColumn<?, ?> colpid;

    @FXML
    private TableColumn<?, ?> colpname;

    @FXML
    private TableColumn<?, ?> colpprice;

    @FXML
    private TableColumn<?, ?> colpqty;

    @FXML
    private AnchorPane product;

    @FXML
    private TextField txtpname;

    @FXML
    private TextField txtpprice;

    @FXML
    private TextField txtpqty;

    @FXML
    void btnDeleteOnActionproduct(ActionEvent event) throws SQLException {
        String productId = lblpid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {


            boolean isDeleted = productModel.deleteProduct(productId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Product deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete product...!").show();
            }
        }
    }

    @FXML
    void btnSaveOnActionproduct(ActionEvent event) throws SQLException {
            String productId =lblpid.getText();
            String productName = txtpname.getText();
            String productPrice = txtpprice.getText();
            String productQty = txtpqty.getText();

            txtpname.setStyle(txtpname.getStyle() + ";-fx-border-color: #7367F0;");
            txtpprice.setStyle(txtpprice.getStyle() + ";-fx-border-color: #7367F0;");
            txtpqty.setStyle(txtpqty.getStyle() + ";-fx-border-color: #7367F0;");

            String productNamePattern = "^[A-Za-z ]+$";
            String productPricePattern = "^(\\d+)(\\.\\d{1,2})?$";;
            String productQtyPattern = "^\\d+$";


            boolean isValidName =productName .matches(productNamePattern);
            boolean isValidPrice =productPrice .matches(productPricePattern);
            boolean isValidQty =productQty.matches(productQtyPattern);

            if (!isValidName) {
                System.out.println(txtpname.getStyle());
                txtpname.setStyle("-fx-border-color: #7367F0;");
                System.out.println("Invalid Name");
                }
            if (!isValidPrice) {
                txtpprice.setStyle("-fx-border-color: red;");
            }
            if (!isValidQty) {
                txtpqty.setStyle("-fx-border-color: red;");
            }

        if (isValidName && isValidPrice && isValidQty) {

            ProductDto productDto = new ProductDto(
                    productId,
                    productName,
                    productPrice,
                    productQty
            );

            boolean isSaved = productModel.saveProduct(productDto);
            if (isSaved) {
                loadTableData();
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Product saved...!").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Fail to save product!").show();
            }
        }

    }

    @FXML
    void btnUpdateOnActionproduct(ActionEvent event) throws SQLException {
       String ProductId = lblpid.getText();
       String ProductName = txtpname.getText();
       String ProductPrice = txtpprice.getText();
       String ProductQty = txtpqty.getText();

       String productNamePattern = "^[A-Za-z ]+$";
       String productPricePattern = "^\\d+(\\.\\d{1,2})?$";
       String productQtyPattern = "^\\d+$";

        boolean isValidName =ProductName .matches(productNamePattern);
        boolean isValidPrice =ProductPrice .matches(productPricePattern);
        boolean isValidQty =ProductQty.matches(productQtyPattern);

        txtpname.setStyle(null);
        txtpprice.setStyle(null);
        txtpqty.setStyle(null);

        if (!isValidName) {
            txtpname.setStyle("-fx-border-color: red;");
            System.out.println("Invalid name");
        }
        if (!isValidPrice) {
            txtpprice.setStyle("-fx-border-color: red;");
            System.out.println("Invalid price");
        }
        if (!isValidQty) {
            txtpqty.setStyle("-fx-border-color: red;");
            System.out.println("Invalid quantity");
        }
        ProductDto productDto = new ProductDto(
                ProductId,
                ProductName,
                ProductPrice,
                ProductQty
        );

        boolean isUpdated = productModel.updateProduct(productDto);
        if (isUpdated) {
            refreshPage();
        new Alert(Alert.AlertType.INFORMATION, "Product update...!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to update Product...!").show();
        }
    }

    @FXML
    void resetOnActionproduct(ActionEvent event) throws SQLException {
        refreshPage();

    }

    ProductDAOImpl productModel= new ProductDAOImpl();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colpid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colpname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colpprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colpqty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        try {
            refreshPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load product data").show();

        }
    }

    private void refreshPage() throws SQLException {

        loadNextProductId();
        loadTableData();

        btnpDelete.setDisable(true);
        btnpUpdate.setDisable(true);
        btnpSave.setDisable(false);

        clearFormFields();

    }

    private void clearFormFields() {

        txtpname.clear();
        txtpprice.clear();
        txtpqty.clear();

    }

    private void loadTableData() throws SQLException {
        ArrayList<ProductDto> productDtos = productModel.getAllProducts();
        ObservableList<ProductTm > productTms = FXCollections.observableArrayList();

        for (ProductDto productDto : productDtos) {
            ProductTm productTm = new ProductTm(
                    productDto.getId(),
                    productDto.getName(),
                    productDto.getPrice(),
                    productDto.getQty()

            );
            productTms.add(productTm);
        }
        tblproo.setItems(productTms);

    }
    private void loadNextProductId() throws SQLException {
        String nextCustomerId = productModel.getNextProduct();
        lblpid.setText(nextCustomerId);
    }


    public void onClickproductTable(MouseEvent mouseEvent) {
        ProductTm productTm = (ProductTm) tblproo.getSelectionModel().getSelectedItem();
        if (productTm != null) {
            lblpid.setText(productTm.getId());
            txtpname.setText(productTm.getName());
            txtpprice.setText(productTm.getPrice());
            txtpqty.setText(productTm.getQty());

            btnpSave.setDisable(true);

            btnpUpdate.setDisable(false);
            btnpDelete.setDisable(false);

        }
    }
}
