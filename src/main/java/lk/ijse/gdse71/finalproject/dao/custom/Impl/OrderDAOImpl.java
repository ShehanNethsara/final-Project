package lk.ijse.gdse71.finalproject.dao.custom.Impl;

import lk.ijse.gdse71.finalproject.dao.custom.OrderDAO;
import lk.ijse.gdse71.finalproject.db.DBConnection;
import lk.ijse.gdse71.finalproject.dto.OrderDto;
import lk.ijse.gdse71.finalproject.dto.PaymentDto;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAOImpl implements OrderDAO {

    private final ContainDAOImpl containModel = new ContainDAOImpl();
    private final PaymentDAOImpl paymentModel = new PaymentDAOImpl();

    public boolean save(OrderDto orderDto , PaymentDto paymentDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);
            System.out.println(paymentDto);
            boolean isSavePayment = paymentModel.save(paymentDto);

            if (isSavePayment) {
                System.out.println("Payment saved successfully");

                boolean isOrderSaved = CrudUtil.execute(
                        "INSERT INTO orders VALUES (?,?,?,?)",
                        orderDto.getOrderId(),
                        orderDto.getOrderDate(),
                        orderDto.getCustomerId(),
                        orderDto.getPaymentId()
                );

                if (isOrderSaved) {
                    System.out.println("Order saved successfully");
                    // Save related contain data
                    boolean isContainerSaved = containModel.saveContain(orderDto.getGetContainDtos());

                    if (isContainerSaved) {
                        connection.commit();
                        return true;
                    } else {
                        connection.rollback();
                        return false;
                    }
                }

            }
            connection.rollback();
            return false;



        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
    }

    public String getNext() throws SQLException {
        try {
            ResultSet rst = CrudUtil.execute("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1");

            if (rst.next()) {
                String lastId = rst.getString(1);
                String substring = lastId.substring(1);
                int newIdIndex = Integer.parseInt(substring) + 1;
                return String.format("O%03d", newIdIndex);
            }
        } catch (NumberFormatException e) {
            throw new SQLException("Error parsing order ID", e);
        }

        // Default ID if no records exist
        return "O001";
    }


}
