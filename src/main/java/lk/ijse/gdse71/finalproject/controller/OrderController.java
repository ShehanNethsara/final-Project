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
import lk.ijse.gdse71.finalproject.db.DBConnection;
import lk.ijse.gdse71.finalproject.dto.*;
import lk.ijse.gdse71.finalproject.dto.tm.OrderTm;
import lk.ijse.gdse71.finalproject.dao.custom.Impl.CustomerDAOImpl;
import lk.ijse.gdse71.finalproject.dao.custom.Impl.OrderDAOImpl;
import lk.ijse.gdse71.finalproject.dao.custom.Impl.PaymentDAOImpl;
import lk.ijse.gdse71.finalproject.dao.custom.Impl.ProductDAOImpl;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderController implements Initializable {

    @FXML
    public Label lblpayid;

    @FXML
    public Label lblcutname;

    @FXML
    public Label lblorderDate;

    @FXML
    public Label lblorderid;
    
    @FXML
    public Button btnpayAddToCart;

    @FXML
    public Label lblqtyOnHand;


    @FXML
    public Label lblPprice;

    @FXML
    private Button btnpay;

    @FXML
    private Button btnreport;

    @FXML
    private Button btnreset;

    @FXML
    private ComboBox<?> cmbPaymethod;

    @FXML
    private TableColumn<?, ?> colcid;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableColumn<?, ?> coloid;

    @FXML
    private TableColumn<?, ?> colpayid;

    @FXML
    private TableColumn<?, ?> colpid;

    @FXML
    private TableColumn<?, ?> colprice;

    @FXML
    private TableColumn<?, ?> colqty;

    @FXML
    private ComboBox<String> cusidcombobox;

    @FXML
    private Label lblbalance;

    @FXML
    private Label lblnamee;

    @FXML
    private Label lbltotal;

    @FXML
    private Label orderDate;

    @FXML
    private AnchorPane orderpage;

    @FXML
    private AnchorPane orderplce;

    @FXML
    private AnchorPane ordertblanchorpane;

    @FXML
    private ComboBox<String> proidcomboc;

    @FXML
    private TableView<OrderTm> tblorder;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtdiscount;

    @FXML
    private TextField txtqty;


        private final OrderDAOImpl orderModel = new OrderDAOImpl();
        private final ProductDAOImpl productModel = new ProductDAOImpl();
        private final CustomerDAOImpl customerModel = new CustomerDAOImpl();
        private final PaymentDAOImpl paymentModel = new PaymentDAOImpl();

        private final ObservableList<OrderTm> orderTms = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();

        loadNextPaymentId();

        try {
            loadCustomerId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            loadProductId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try{
            refreshPage();
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Fail to load data....");
        }

    }
    private void loadNextPaymentId() {
        try {
            lblpayid.setText(paymentModel.getNextPayment());
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Fail to load payment Id....");
        }
    }

    private void refreshPage() throws SQLException {
        lblorderid.setText(orderModel.getNextOrderId());
        lblorderDate.setText(LocalDate.now().toString());

        lblorderDate.setText(LocalDate.now().toString());

        loadCustomerId();
        loadProductId();


        cusidcombobox.getSelectionModel().clearSelection();
        proidcomboc.getSelectionModel().clearSelection();
        cmbPaymethod.getSelectionModel().clearSelection();
        lblcutname.setText("");
        lblnamee.setText("");
        lblPprice.setText("");
        lbltotal.setText("");
        lblbalance.setText("");
        txtdiscount.setText("");
        txtqty.setText("");
        txtAmount.setText("");
        lblqtyOnHand.setText("");

        orderTms.clear();

        tblorder.refresh();

    }

    private void loadCustomerId() throws SQLException {
        ArrayList<String> customerIds = customerModel.getAllCustomerIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(customerIds);
        cusidcombobox.setItems(observableList);
    }

    private void loadProductId() throws SQLException {
        ArrayList<String> productids = productModel.getAllProductids();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(productids);
        proidcomboc.setItems(observableList);
    }



    private void setCellValues() {
        colpid.setCellValueFactory(new PropertyValueFactory<>("ProductId"));
        colname.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colprice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colcid.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));

        tblorder.setItems(orderTms);
    }

    public void onActionAddToCart(ActionEvent actionEvent) {

        if ( proidcomboc.getSelectionModel().getSelectedItem() != null ) {

            String selectedProductId = proidcomboc.getSelectionModel().getSelectedItem();
            if (cusidcombobox.getSelectionModel().getSelectedItem() != null) {
                String selectedCustomerId = cusidcombobox.getSelectionModel().getSelectedItem();


                if (selectedProductId == null || selectedCustomerId == null) {
                    new Alert(Alert.AlertType.ERROR, "Please select product id and customer id...!");
                    return;
                }
            }else return;

        }else return;
        String selectedProductId = proidcomboc.getSelectionModel().getSelectedItem();
        String selectedCustomerId = cusidcombobox.getSelectionModel().getSelectedItem();

        // Input validation
        String QtyString = txtqty.getText();
        String AmountString = txtAmount.getText();
        String discountString = txtdiscount.getText();

        String qtyPattern = "^[1-9][0-9]*$";
        String amountPattern = "^[0-9]+(\\.[0-9]{1,2})?$";
        String discountPattern = "^(100|[1-9]?[0-9])(\\.[0-9]{1,2})?$";

        if (!QtyString.matches(qtyPattern)) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid quantity!");
            return;
        }
        if (!AmountString.matches(amountPattern)) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid amount!");
            return;
        }
        if (!discountString.matches(discountPattern)) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid discount percentage!");
            return;
        }
        String productName = lblnamee.getText();
        double price = Double.parseDouble(lblPprice.getText());

        int qty = Integer.parseInt(QtyString);
        double amount = Double.parseDouble(AmountString);
        double discountPercentage = Double.parseDouble(discountString);


        int qtyOnHand = Integer.parseInt(lblqtyOnHand.getText());
        if (qty > qtyOnHand) {
            new Alert(Alert.AlertType.ERROR, "Insufficient stock. Please reduce the quantity.");
            return;
        }
        txtqty.setText("");
        // Calculate total and discount

        double total=price * qty;
        lbltotal.setText(String.valueOf(total));
        double discountAmount = (total * discountPercentage) / 100;
       double balance =  amount - (total - discountAmount);
        lblbalance.setText(String.valueOf(balance));

        for (OrderTm orderTm : orderTms) {
            if (orderTm.getProductId().equals(selectedProductId)) {
                int newQty = orderTm.getQuantity() + qty;
                orderTm.setQuantity(newQty);
                orderTm.setQuantity(newQty);
                orderTms.add(orderTm);
                tblorder.refresh();
                return;
            }
        }
        Button btn = new Button("Remove");
        // Add to Order Table
        OrderTm newOrderTm = new OrderTm(
                selectedProductId,
                productName,
                qty,
                price,
                selectedCustomerId

        );

        btn.setOnAction(actionEvent1 ->  {
                orderTms.remove(newOrderTm);

        tblorder.refresh();
    });
        orderTms.add(newOrderTm);

    }

    @FXML
    void onActionpay(ActionEvent event) throws SQLException {
        if (tblorder.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please  add Product to the table!");
            return;
        }
        if (proidcomboc.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select product id!");
            return;

        }
        if (cusidcombobox.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select customer for place order!");
            return;
        }

        String orderId = lblorderid.getText();
        Date dataOfOrder = Date.valueOf(lblorderDate.getText());
        String customerId = cusidcombobox.getValue();
        String paymentId = lblpayid.getText();
        int discount = Integer.parseInt(txtdiscount.getText());

        ArrayList<ContainDto> containDtos = new ArrayList<>();

        for (OrderTm orderTm : orderTms) {
            ContainDto containDto = new ContainDto(
                    orderId,
                    orderTm.getProductId(),
                    orderTm.getQuantity()
            );
            containDtos.add(containDto);

            tblorder.refresh();


        }
        OrderDto orderDto = new OrderDto(
                orderId,
                dataOfOrder,
                customerId,
                paymentId,
                containDtos
        );
        PaymentDto paymentDto = new PaymentDto(
                lblpayid.getText(),
                discount,
                dataOfOrder

        );
        boolean isSaved = orderModel. saveOrder(orderDto,paymentDto);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Order added successfully!");
            refreshPage();
          } else {
            new Alert(Alert.AlertType.ERROR, "Order fail..!");
        }

    }
    @FXML
    public void cmbOnactionProId(ActionEvent actionEvent) throws SQLException {
            String selectedProId = proidcomboc.getSelectionModel().getSelectedItem();
            ProductDto productDto = productModel.findByPid(selectedProId);

            if (productDto != null) {
                lblnamee.setText(productDto.getName());
                lblqtyOnHand.setText(productDto.getQty());
                lblPprice.setText(productDto.getPrice());
            }
    }

        @FXML
    public   void cmbOnactioncusId(ActionEvent actionEvent) throws SQLException {
        String selectedCustomerId = cusidcombobox.getSelectionModel().getSelectedItem();
        CustomerDto customerDto = customerModel.findByCid(selectedCustomerId);

        if (customerDto != null) {
            lblcutname.setText(customerDto.getCustomerName());
        }
    }
    @FXML
    public void btnOnActionReset(ActionEvent actionEvent) throws SQLException {
        refreshPage();
    }

    public void orderReportOnAction(ActionEvent actionEvent) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/report/order_report.jrxml"
                            ));

            Connection connection = DBConnection.getInstance().getConnection();

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    null,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to generate order report...!").show();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB error order...!").show();
        }
    }

    public void orderTableMouseClick(MouseEvent mouseEvent) {
        OrderTm orderTm = tblorder.getSelectionModel().getSelectedItem();
        //int qty = Integer.parseInt(txtqty.getText());
        //int price = Integer.parseInt(lbltotal.getText());

        if (orderTm != null) {
                proidcomboc.setValue(orderTm.getProductId());
                lblnamee.setText(orderTm.getProductName());
                txtqty.setText(String.valueOf(orderTm.getQuantity()));
                lbltotal.setText(String.valueOf(orderTm.getPrice()));
                cusidcombobox.setValue(orderTm.getCustomerId());

                btnreport.setDisable(false);
                btnreset.setDisable(false);
                btnpayAddToCart.setDisable(true);

        }
    }
}








