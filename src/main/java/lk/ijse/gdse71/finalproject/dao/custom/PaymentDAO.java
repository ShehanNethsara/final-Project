package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dao.CrudDAO;
import lk.ijse.gdse71.finalproject.dto.PaymentDto;

import java.sql.SQLException;

public interface PaymentDAO extends CrudDAO {

    String getNextPayment() throws SQLException;

     boolean savePayment (PaymentDto paymentDto) throws SQLException;
}
