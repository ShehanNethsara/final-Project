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
import lk.ijse.gdse71.finalproject.dto.EmployeeDto;
import lk.ijse.gdse71.finalproject.dto.tm.EmployeeTm;
import lk.ijse.gdse71.finalproject.dao.custom.Impl.EmployeeDAOImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    @FXML
    private Button btneDelete;

    @FXML
    private Button btneReset;

    @FXML
    private Button btneSave;

    @FXML
    private Button btneUpdate;

    @FXML
    private TableColumn<EmployeeTm, String> coleaddress;

    @FXML
    private TableColumn<EmployeeTm, String> coleconnumber;

    @FXML
    private TableColumn<EmployeeTm, String> coleid;

    @FXML
    private TableColumn<EmployeeTm, String> colejobroll;

    @FXML
    private TableColumn<EmployeeTm, String> colename;

    @FXML
    private TableColumn<EmployeeTm, String> colesalary;

    @FXML
    private AnchorPane employeePage;

    @FXML
    private Label lbleid;

    @FXML
    private TableView<EmployeeTm> tblemployee;

    @FXML
    private TextField txteAddress;

    @FXML
    private TextField txteJobroll;

    @FXML
    private TextField txteSalary;

    @FXML
    private TextField txtecontact;

    @FXML
    private TextField txtename;

    @FXML
    void btnDeleteOnActionEmployee(ActionEvent event) throws Exception {
        String employeeId = lbleid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = employeeModel.deleteEmployee(employeeId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Employee deleted successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee not deleted").show();
            }
        }
    }

    @FXML
    void btnSaveOnActionEmployee(ActionEvent event) throws Exception {
        String employeeId = lbleid.getText();
        String employeeName = txtename.getText();
        String Address = txteAddress.getText();
        String Salary = txteSalary.getText();
        String JobRoll = txteJobroll.getText();
        String ContactNumber = txtecontact.getText();

        txtename.setStyle("-fx-border-color: #7367F0;");
        txteAddress.setStyle("-fx-border-color: #7367F0;");
        txteSalary.setStyle("-fx-border-color: #7367F0;");
        txteJobroll.setStyle("-fx-border-color: #7367F0;");
        txtecontact.setStyle("-fx-border-color: #7367F0;");

        String employeeNamePattern = "^[A-Za-z ]+$";
        String AddressPattern = "^[A-Za-z0-9#,.\\-\\s]+$";
        String SalaryPattern = "^\\d+(\\.\\d{1,2})?$";
        String jobRollPattern = "^[A-Za-z ]+$";
        String ContactNumberPattern = "^\\+?[0-9]{10,15}$";

        boolean isValidName = employeeName.matches(employeeNamePattern);
        boolean isValidAddress = Address.matches(AddressPattern);
        boolean isValidSalary = Salary.matches(SalaryPattern);
        boolean isValidjobRoll = JobRoll.matches(jobRollPattern);
        boolean isValidContactNumber = ContactNumber.matches(ContactNumberPattern);

        if (!isValidName) txtename.setStyle("-fx-border-color: red;");
        if (!isValidAddress) txteAddress.setStyle("-fx-border-color: red;");
        if (!isValidSalary) txteSalary.setStyle("-fx-border-color: red;");
        if (!isValidjobRoll) txteJobroll.setStyle("-fx-border-color: red;");
        if (!isValidContactNumber) txtecontact.setStyle("-fx-border-color: red;");

        if (isValidName && isValidAddress && isValidSalary && isValidjobRoll && isValidContactNumber) {
            EmployeeDto employeeDto = new EmployeeDto(employeeId, employeeName, Address, Salary, JobRoll, ContactNumber);
            boolean isSaved = EmployeeDAOImpl.saveEmployee(employeeDto);

            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Employee saved successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee not saved").show();
            }
        }
    }

    @FXML
    void btnUpdateOnActionEmployee(ActionEvent event) throws Exception {
        String employeeId = lbleid.getText();
        String employeeName = txtename.getText();
        String Address = txteAddress.getText();
        String Salary = txteSalary.getText();
        String JobRoll = txteJobroll.getText();
        String ContactNumber = txtecontact.getText();

        txtename.setStyle("-fx-border-color: #7367F0;");
        txteAddress.setStyle("-fx-border-color: #7367F0;");
        txteSalary.setStyle("-fx-border-color: #7367F0;");
        txteJobroll.setStyle("-fx-border-color: #7367F0;");
        txtecontact.setStyle("-fx-border-color: #7367F0;");


        String employeeNamePattern = "^[A-Za-z ]+$";
        String AddressPattern = "^[A-Za-z0-9#,.\\-\\s]+$";
        String SalaryPattern = "^\\d+(\\.\\d{1,2})?$";
        String jobRollPattern = "^[A-Za-z ]+$";
        String ContactNumberPattern = "^\\+?[0-9]{10,15}$";

        boolean isValidName = employeeName.matches(employeeNamePattern);
        boolean isValidAddress = Address.matches(AddressPattern);
        boolean isValidSalary = Salary.matches(SalaryPattern);
        boolean isValidJobRoll = JobRoll.matches(jobRollPattern);
        boolean isValidContactNumber = ContactNumber.matches(ContactNumberPattern);


        if (!isValidName) txtename.setStyle("-fx-border-color: red;");
        if (!isValidAddress) txteAddress.setStyle("-fx-border-color: red;");
        if (!isValidSalary) txteSalary.setStyle("-fx-border-color: red;");
        if (!isValidJobRoll) txteJobroll.setStyle("-fx-border-color: red;");
        if (!isValidContactNumber) txtecontact.setStyle("-fx-border-color: red;");

        // Proceed if all validations pass
        if (isValidName && isValidAddress && isValidSalary && isValidJobRoll && isValidContactNumber) {
            EmployeeDto employeeDto = new EmployeeDto(
                    employeeId,
                    employeeName,
                    Address,
                    Salary,
                    JobRoll,
                    ContactNumber
            );

            try {
                boolean isUpdated = EmployeeDAOImpl.updateEmployee(employeeDto);
                if (isUpdated) {
                    refreshPage();
                    new Alert(Alert.AlertType.INFORMATION, "Employee updated successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Employee not updated").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to update employee").show();
            }
        }
    }


    @FXML
    void onclickemployeetbl(MouseEvent event) {
        EmployeeTm employeeTm = tblemployee.getSelectionModel().getSelectedItem();
        if (employeeTm != null) {
            lbleid.setText(employeeTm.getEmployeeId());
            txtename.setText(employeeTm.getEmployeeName());
            txteAddress.setText(employeeTm.getAddress());
            txteSalary.setText(employeeTm.getSalary());
            txteJobroll.setText(employeeTm.getJobRoll());
            txtecontact.setText(employeeTm.getContactNumber());

            btneSave.setDisable(true);
            btneUpdate.setDisable(false);
            btneDelete.setDisable(false);
        }
    }

    @FXML
    void resetOnActionEmployee(ActionEvent event) throws Exception {
        refreshPage();
    }

    EmployeeDAOImpl employeeModel = new EmployeeDAOImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        coleid.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colename.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        coleaddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colesalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colejobroll.setCellValueFactory(new PropertyValueFactory<>("jobRoll"));
        coleconnumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load employee data").show();
        }
    }

    private void refreshPage() throws Exception {
        loadNextEmployeeId();
        loadTableData();

        btneSave.setDisable(false);
        btneUpdate.setDisable(true);
        btneDelete.setDisable(true);

        clearFormFields();
    }

    private void clearFormFields() {
        txteAddress.clear();
        txteJobroll.clear();
        txteSalary.clear();
        txtecontact.clear();
        txtename.clear();
    }

    private void loadTableData() throws Exception {
        ArrayList<EmployeeDto> employeeDtos = employeeModel.getAllEmployee();
        ObservableList<EmployeeTm> employeeTms = FXCollections.observableArrayList();

        for (EmployeeDto employeeDto : employeeDtos) {
            EmployeeTm employeeTm = new EmployeeTm(
                    employeeDto.getEmployeeId(),
                    employeeDto.getEmployeeName(),
                    employeeDto.getAddress(),
                    employeeDto.getSalary(),
                    employeeDto.getJobRoll(),
                    employeeDto.getContactNumber()
            );
            employeeTms.add(employeeTm);
        }
        tblemployee.setItems(employeeTms);
    }

    private void loadNextEmployeeId() throws SQLException {
        String nextEmployeeId = employeeModel.getNextEmployee();
        lbleid.setText(nextEmployeeId);
    }
}
