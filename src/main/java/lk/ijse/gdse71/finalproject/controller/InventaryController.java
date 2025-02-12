package lk.ijse.gdse71.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse71.finalproject.dto.EmployeeDto;
import lk.ijse.gdse71.finalproject.dto.InventaryDto;
import lk.ijse.gdse71.finalproject.dto.SupplierDto;
import lk.ijse.gdse71.finalproject.dto.tm.InventaryTm;
import lk.ijse.gdse71.finalproject.dao.custom.Impl.EmployeeDAOImpl;
import lk.ijse.gdse71.finalproject.dao.custom.Impl.InventaryDAOImpl;
import lk.ijse.gdse71.finalproject.dao.custom.Impl.SupplierDAOImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class InventaryController implements Initializable {


    @FXML
    public Label lblempname;
    @FXML
    public Label lblsupname;
    @FXML
    private Button btniDelete;

    @FXML
    private Button btniRest;

    @FXML
    private Button btniSave;

    @FXML
    private Button btniUpdate;

    @FXML
    private TableColumn<InventaryTm,String> colides;

    @FXML
    private TableColumn<InventaryTm,String> coliemployeeId;

    @FXML
    private TableColumn<InventaryTm,String> coliinventaryId;

    @FXML
    private TableColumn<InventaryTm, String> colinamee;

    @FXML
    private TableColumn<InventaryTm, String> coliqty;

    @FXML
    private TableColumn<InventaryTm,String> colisupid;

    @FXML
    private ComboBox<String> empidcombox;

    @FXML
    private AnchorPane inventaryPage;

    @FXML
    private Label lblinventaryid;

    @FXML
    private ComboBox<String> supidcombox;

    @FXML
    private TableView<InventaryTm> tblInventary;

    @FXML
    private TextField txtiName;

    @FXML
    private TextField txtides;

    @FXML
    private TextField txtiqty;

    private final SupplierDAOImpl supplierModel = new SupplierDAOImpl();
    private final EmployeeDAOImpl employeeModel = new EmployeeDAOImpl();

    @FXML
    void OnClicktblinventary(MouseEvent event) {
        InventaryTm inventaryTm = (InventaryTm) tblInventary.getSelectionModel().getSelectedItem();
        if (inventaryTm != null) {

            empidcombox.getValue();
            lblinventaryid.setText(inventaryTm.getInventaryId());
            txtiName.setText(inventaryTm.getName());
            txtiqty.setText(inventaryTm.getQty());
            txtides.setText(inventaryTm.getDescription());

            btniSave.setDisable(true);
            btniUpdate.setDisable(false);
            btniDelete.setDisable(false);

        }

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws Exception {
        String employeeId = lblinventaryid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = inventaryModel.deleteEmployee(employeeId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Inventary deleted successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Inventary not deleted").show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws Exception {
        String employeeId = empidcombox.getValue();
        String inventaryId = lblinventaryid.getText();
        String name = txtiName.getText();
        String qty = txtiqty.getText();
        String des = txtides.getText();
        String supplierId = supidcombox.getValue();

        txtiqty.setStyle("-fx-border-color: #7367F0;");
        txtiName.setStyle("-fx-border-color: #7367F0;");
        txtides.setStyle("-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String qtyPattern = "^\\d+$";
        String desPattern = "^[A-Za-z0-9\\s]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidQty = qty.matches(qtyPattern);
        boolean isValidDes = des.matches(desPattern);

        if (!isValidName) {
            System.out.println(txtiName.getStyle());
            txtiName.setStyle("-fx-border-color: #7367F0;");
            System.out.println("Invalid Name");
        }
        if (!isValidQty) {
            txtiqty.setStyle("-fx-border-color: #7367F0;");
        }
        if (!isValidDes) {
            txtides.setStyle("-fx-border-color: #7367F0;");
        }

        if (isValidName && isValidQty && isValidDes) {
            InventaryDto inventaryDto = new InventaryDto(
                    employeeId,
                    inventaryId,
                    name,
                    qty,
                    des
            );
            boolean isSaved = inventaryModel.saveInventary(inventaryDto,supplierId);

            if (isSaved) {
                loadTableData();
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Inventary saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Inventary!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws Exception {

        String employeeId = empidcombox.getValue();
        String inventaryId = lblinventaryid.getText();
        String name = txtiName.getText();
        String qty = txtiqty.getText();
        String des = txtides.getText();

        txtiqty.setStyle("-fx-border-color: #7367F0;");
        txtiName.setStyle("-fx-border-color: #7367F0;");
        txtides.setStyle("-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String qtyPattern = "^\\d+$";
        String desPattern = "^[A-Za-z0-9\\s]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidQty = qty.matches(qtyPattern);
        boolean isValidDes = des.matches(desPattern);

        if (!isValidName) {
            System.out.println(txtiName.getStyle());
            txtiName.setStyle("-fx-border-color: #7367F0;");
            System.out.println("Invalid Name");
        }
        if (!isValidQty) {
            txtiqty.setStyle("-fx-border-color: #7367F0;");
        }
        if (!isValidDes) {
            txtides.setStyle("-fx-border-color: #7367F0;");
        }

        if (isValidName && isValidQty && isValidDes) {
            InventaryDto inventaryDto = new InventaryDto(
                    employeeId,
                    inventaryId,
                    name,
                    qty,
                    des
            );
           try {
               boolean isUpdate = inventaryModel.updateInventary(inventaryDto);

               if (isUpdate) {
                   loadTableData();
                   refreshPage();
                   new Alert(Alert.AlertType.INFORMATION, "Inventary update...!").show();
               } else {
                   new Alert(Alert.AlertType.ERROR, "Inventary not update !").show();
               }
           } catch (Exception e) {
               e.printStackTrace();
               new Alert(Alert.AlertType.ERROR, "Fail to update Inventary!").show();
           }
        }
    }

    @FXML
    void resetOnAction(ActionEvent event) throws Exception {
        refreshPage();
    }

    InventaryDAOImpl inventaryModel = new InventaryDAOImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        coliemployeeId.setCellValueFactory(new PropertyValueFactory<>("EmployeeId"));
        coliinventaryId.setCellValueFactory(new PropertyValueFactory<>("InventaryId"));
        colinamee.setCellValueFactory(new PropertyValueFactory<>("Name"));
        coliqty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colides.setCellValueFactory(new PropertyValueFactory<>("Description"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load inventary data").show();
        }

    }

    private void refreshPage() throws Exception {

        loadNextInventaryId();
        loadTableData();
        setEmployee();
        setSupplier();


        btniSave.setDisable(false);
        btniUpdate.setDisable(true);
        btniDelete.setDisable(true);

        clearFormFields();
    }

    private void clearFormFields() {

        txtiName.clear();
        txtides.clear();
        txtiqty.clear();
    }

    private void loadTableData() throws SQLException {
        ArrayList<InventaryDto> inventaryDtos = inventaryModel.getAllInventory();
        ObservableList<InventaryTm> inventaryTms = FXCollections.observableArrayList();

        System.out.println(inventaryDtos);
        for (InventaryDto inventaryDto : inventaryDtos) {
            InventaryTm inventaryTm = new InventaryTm(
                    inventaryDto.getEmployeeId(),
                    inventaryDto.getInventaryId(),
                    inventaryDto.getName(),
                    inventaryDto.getQty(),
                    inventaryDto.getDescription()
            );
            inventaryTms.add(inventaryTm);
        }
        tblInventary.setItems(inventaryTms);
    }

    private void loadNextInventaryId() throws SQLException {
        String nextEmployeeId = inventaryModel.getNextInventory();
        lblinventaryid.setText(nextEmployeeId);
    }

     @FXML
     void btnaddnewSupplierOnAction(ActionEvent actionEvent) {
       loadpage ("/view/Supplier.fxml");
    }

    public void btnaddnewEmlpoyeeeOnAction(ActionEvent actionEvent) {
        loadpage("/view/Employee.fxml");

    }

    private void loadpage(String fxmlPath)   {
        try {
            inventaryPage.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            inventaryPage.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load page data").show();
        }
    }

    void setEmployee() throws SQLException {

        ArrayList<EmployeeDto> employeeDtos = employeeModel.getAllEmployee();
        ObservableList<String> strings = FXCollections.observableArrayList();

        for (EmployeeDto employeeDto : employeeDtos) {
            strings.add(employeeDto.getEmployeeId());
        }

        empidcombox.setItems(strings);
    }

     void setSupplier() throws SQLException {
        ArrayList<SupplierDto> supplierDtos = supplierModel.getAllSuppliers();
        ObservableList<String> strings = FXCollections.observableArrayList();

        for (SupplierDto supplierDto : supplierDtos) {
            strings.add(supplierDto.getId());
        }

        supidcombox.setItems(strings);
    }

    public void cmbSupplieridOnAction(ActionEvent actionEvent) throws SQLException {
   }

    public void cmbemployeeidOnAction(ActionEvent actionEvent) throws SQLException {

    }
}



