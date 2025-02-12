package lk.ijse.gdse71.finalproject.dao.custom.Impl;

import lk.ijse.gdse71.finalproject.dao.custom.PaymentDAO;
import lk.ijse.gdse71.finalproject.dto.PaymentDto;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentDAOImpl implements PaymentDAO {


    public String getNex() throws SQLException {
        ResultSet rst = CrudUtil.execute("select payment_id from payment order by payment_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("p%03d", newIdIndex);
        }
        return "p001";
    }
    public boolean save (PaymentDto paymentDto) throws SQLException {
        return CrudUtil.execute("INSERT INTO payment VALUES (?,?,?)",
                paymentDto.getPid(),
                paymentDto.getDiscount(),
                paymentDto.getDate()
                );
    }

}
