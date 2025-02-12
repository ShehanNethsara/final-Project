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
import lk.ijse.gdse71.finalproject.dto.SupplierDto;
import lk.ijse.gdse71.finalproject.dto.tm.SupplierTm;
import lk.ijse.gdse71.finalproject.dao.custom.Impl.SupplierDAOImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {

    @FXML
    private Button btnsDelete;

    @FXML
    private Button btnsSave;

    @FXML
    private Button btnsUpdate;

    @FXML
    private Button btnspReset;

    @FXML
    private TableColumn<SupplierTm, String> colsid;

    @FXML
    private TableColumn<SupplierTm, String> colsname;

    @FXML
    private TableColumn<SupplierTm, String> colsaddress;

    @FXML
    private TableColumn<SupplierTm, String> colscompany;

    @FXML
    private TableColumn<SupplierTm, String> colsemail;

    @FXML
    private TableColumn<SupplierTm, String> colsconnumber;

    @FXML
    private Label lblsupid;

    @FXML
    private AnchorPane supplierPage;

    @FXML
    private TableView<SupplierTm> tblsupplier;

    @FXML
    private TextField txtsAddress;

    @FXML
    private TextField txtsCompany;

    @FXML
    private TextField txtsEmail;

    @FXML
    private TextField txtscontact;

    @FXML
    private TextField txtsname;

    private final SupplierDAOImpl supplierModel = new SupplierDAOImpl();

    @FXML
    void btnDeleteOnActionSupplier(ActionEvent event) throws SQLException {
        String supplierId = lblsupid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = supplierModel.deletedSupplier(supplierId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Supplier deleted successfully").showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR, "Supplier not deleted").showAndWait();
            }
        }
    }

    @FXML
    void btnSaveOnActionSupplier(ActionEvent event) throws SQLException {
        String supplierId = lblsupid.getText();
        String supplierName = txtsname.getText();
        String Address = txtsAddress.getText();
        String Company = txtsCompany.getText();
        String Email = txtsEmail.getText();
        String Contact = txtscontact.getText();

        txtsname.setStyle("-fx-border-color: #7367F0;");
        txtsAddress.setStyle("-fx-border-color: #7367F0;");
        txtsCompany.setStyle("-fx-border-color: #7367F0;");
        txtsEmail.setStyle("-fx-border-color: #7367F0;");
        txtscontact.setStyle("-fx-border-color: #7367F0;");

        String supplierNamePattern = "^[A-Za-z ]+$";
        String supplierAddressPattern = "^[A-Za-z0-9#,.\\-\\s]+$";
        String supplierCompanyPattern = "^[A-Za-z0-9&\\-. ]+$";
        String supplierEmailPattern = "^[\\w!#$%&'*+/=?{|}~^-]+(?:\\.[\\w!#$%&'*+/=?{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String supplierContactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = supplierName.matches(supplierNamePattern);
        boolean isValidAddress = Address.matches(supplierAddressPattern);
        boolean isValidCompany = Company.matches(supplierCompanyPattern);
        boolean isValidEmail = Email.matches(supplierEmailPattern);
        boolean isValidContact = Contact.matches(supplierContactPattern);

        if (!isValidName) txtsname.setStyle("-fx-border-color: red;");
        if (!isValidAddress) txtsAddress.setStyle("-fx-border-color: red;");
        if (!isValidCompany) txtsCompany.setStyle("-fx-border-color: red;");
        if (!isValidEmail) txtsEmail.setStyle("-fx-border-color: red;");
        if (!isValidContact) txtscontact.setStyle("-fx-border-color: red;");

        if (isValidName && isValidAddress && isValidCompany && isValidEmail && isValidContact) {
            SupplierDto supplierDto = new SupplierDto(
                    supplierId,
                    supplierName,
                    Address,
                    Company,
                    Email,
                    Contact
            );

            try {
                boolean isSaved = supplierModel.saveSupplier(supplierDto);
                if (isSaved) {
                    refreshPage();
                    new Alert(Alert.AlertType.INFORMATION, "Supplier saved successfully").showAndWait();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Supplier not saved").showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to saved supplier").showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fix the highlighted errors").showAndWait();
        }
    }

    @FXML
    void btnUpdateOnActionSupplier(ActionEvent event) throws SQLException {
        String supplierId = lblsupid.getText();
        String supplierName = txtsname.getText();
        String Address = txtsAddress.getText();
        String Company = txtsCompany.getText();
        String Email = txtsEmail.getText();
        String Contact = txtscontact.getText();

        txtsname.setStyle("-fx-border-color: #7367F0;");
        txtsAddress.setStyle("-fx-border-color: #7367F0;");
        txtsCompany.setStyle("-fx-border-color: #7367F0;");
        txtsEmail.setStyle("-fx-border-color: #7367F0;");
        txtscontact.setStyle("-fx-border-color: #7367F0;");

        String supplierNamePattern = "^[A-Za-z ]+$";
        String supplierAddressPattern = "^[A-Za-z0-9#,.\\-\\s]+$";
        String supplierCompanyPattern = "^[A-Za-z0-9&\\-. ]+$";
        String supplierEmailPattern = "^[\\w!#$%&'*+/=?{|}~^-]+(?:\\.[\\w!#$%&'*+/=?{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String supplierContactPattern = "^\\+?[0-9]{10,15}$";

        boolean isValidName = supplierName.matches(supplierNamePattern);
        boolean isValidAddress = Address.matches(supplierAddressPattern);
        boolean isValidCompany = Company.matches(supplierCompanyPattern);
        boolean isValidEmail = Email.matches(supplierEmailPattern);
        boolean isValidContact = Contact.matches(supplierContactPattern);

        if (!isValidName) txtsname.setStyle("-fx-border-color: red;");
        if (!isValidAddress) txtsAddress.setStyle("-fx-border-color: red;");
        if (!isValidCompany) txtsCompany.setStyle("-fx-border-color: red;");
        if (!isValidEmail) txtsEmail.setStyle("-fx-border-color: red;");
        if (!isValidContact) txtscontact.setStyle("-fx-border-color: red;");

        if (isValidName && isValidAddress && isValidCompany && isValidEmail && isValidContact) {
            SupplierDto supplierDto = new SupplierDto(
                    supplierId,
                    supplierName,
                    Address,
                    Company,
                    Email,
                    Contact
            );

            try {
                boolean isUpdated = supplierModel.updateSupplier(supplierDto);
                if (isUpdated) {
                    refreshPage();
                    new Alert(Alert.AlertType.INFORMATION, "Supplier updated successfully").showAndWait();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Supplier not updated").showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to update supplier").showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fix the highlighted errors").showAndWait();
        }
    }

    @FXML
    void onClicksupTbl(MouseEvent event) {
        SupplierTm selectedSupplier = tblsupplier.getSelectionModel().getSelectedItem();

        if (selectedSupplier != null) {
            lblsupid.setText(selectedSupplier.getId());
            txtsname.setText(selectedSupplier.getName());
            txtsAddress.setText(selectedSupplier.getAddress());
            txtsCompany.setText(selectedSupplier.getCompany());
            txtsEmail.setText(selectedSupplier.getEmail());
            txtscontact.setText(selectedSupplier.getContact());

            btnsDelete.setDisable(false);
            btnsUpdate.setDisable(false);
            btnsSave.setDisable(true);
        }
    }

    @FXML
    void resetOnActionSupplier(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colsid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colsname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colsaddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colscompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        colsemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colsconnumber.setCellValueFactory(new PropertyValueFactory<>("contact"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load supplier data").show();

        }
    }

    private void refreshPage() throws SQLException {
        loadNextProductId();
        loadTableData();

        btnsDelete.setDisable(true);
        btnsUpdate.setDisable(true);
        btnsSave.setDisable(false);

        clearFormFields();
    }

    private void clearFormFields() {
        txtsname.clear();
        txtsAddress.clear();
        txtsCompany.clear();
        txtsEmail.clear();
        txtscontact.clear();
    }

    private void loadTableData() throws SQLException {
        ArrayList<SupplierDto> supplierDtos = supplierModel.getAllSuppliers();
        ObservableList<SupplierTm> supplierTms = FXCollections.observableArrayList();

        for (SupplierDto supplierDto : supplierDtos) {
            SupplierTm supplierTm = new SupplierTm(
                    supplierDto.getId(),
                    supplierDto.getName(),
                    supplierDto.getAddress(),
                    supplierDto.getCompany(),
                    supplierDto.getEmail(),
                    supplierDto.getContact()
            );
            supplierTms.add(supplierTm);
        }
        tblsupplier.setItems(supplierTms);
    }

    private void loadNextProductId() throws SQLException {
        String nextSupplierId = supplierModel.getNextSupplier();
        lblsupid.setText(nextSupplierId);
    }
}
