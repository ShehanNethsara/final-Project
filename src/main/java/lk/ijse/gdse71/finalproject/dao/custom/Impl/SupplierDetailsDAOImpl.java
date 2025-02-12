package lk.ijse.gdse71.finalproject.dao.custom.Impl;

import lk.ijse.gdse71.finalproject.dao.custom.SupplierDetailsDAO;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.sql.SQLException;

public class SupplierDetailsDAOImpl implements SupplierDetailsDAO {

    public  boolean save(String inventaryId, String supplierId) throws SQLException {

        return CrudUtil.execute(
                "INSERT into supplier_details values (?,?)",
                inventaryId,supplierId
        );
    }
}
