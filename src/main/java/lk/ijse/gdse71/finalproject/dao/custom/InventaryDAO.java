package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dao.CrudDAO;
import lk.ijse.gdse71.finalproject.dto.InventaryDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InventaryDAO extends CrudDAO {

    String getNextInventory() throws SQLException ;

    ArrayList<InventaryDto> getAllInventory() throws SQLException ;

     boolean deleteEmployee(String employeeId) throws SQLException ;

    boolean saveInventary(InventaryDto inventaryDto, String supplierId) throws SQLException;

     boolean updateInventary(InventaryDto inventaryDto) throws SQLException ;
}
