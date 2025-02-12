package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dao.CrudDAO;
import lk.ijse.gdse71.finalproject.dto.CustomerDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface  CustomerDAO extends CrudDAO <CustomerDto> {



     CustomerDto findByCid(String selectedCusId) throws SQLException ;

     ArrayList<String> getAllCustomerIds() throws SQLException ;
}
