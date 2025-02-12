package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dao.CrudDAO;

import java.sql.SQLException;

public interface SupplierDetailsDAO extends CrudDAO {

    boolean save(String inventaryId, String supplierId) throws SQLException ;

    }
