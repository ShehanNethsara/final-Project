package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dao.CrudDAO;
import lk.ijse.gdse71.finalproject.dto.ContainDto;
import lk.ijse.gdse71.finalproject.dto.ProductDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDAO extends CrudDAO  <ProductDto> {


     ProductDto findByPid(String selectedProId) throws SQLException ;

     boolean reduceQty(ContainDto containDto) throws SQLException;

     ArrayList<String> getAllProductids() throws SQLException;
}
