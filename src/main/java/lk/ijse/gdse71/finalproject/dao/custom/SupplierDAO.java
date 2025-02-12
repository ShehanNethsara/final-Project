package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dao.CrudDAO;
import lk.ijse.gdse71.finalproject.dto.SupplierDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierDAO  extends CrudDAO {

     ArrayList<SupplierDto> getAllSuppliers() throws SQLException ;

     String getNextSupplier() throws SQLException ;

     boolean deletedSupplier(String supplierId) throws SQLException ;

     boolean updateSupplier(SupplierDto supplierDto) throws SQLException ;

     boolean saveSupplier(SupplierDto supplierDto) throws SQLException ;
}
