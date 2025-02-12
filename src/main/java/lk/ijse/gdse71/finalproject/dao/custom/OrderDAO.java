package lk.ijse.gdse71.finalproject.dao.custom;


import lk.ijse.gdse71.finalproject.dao.CrudDAO;
import lk.ijse.gdse71.finalproject.dto.OrderDto;
import lk.ijse.gdse71.finalproject.dto.PaymentDto;

import java.sql.SQLException;

public interface OrderDAO extends CrudDAO {

     boolean saveOrder(OrderDto orderDto , PaymentDto paymentDto) throws SQLException ;

     String getNextOrderId() throws SQLException ;
}
