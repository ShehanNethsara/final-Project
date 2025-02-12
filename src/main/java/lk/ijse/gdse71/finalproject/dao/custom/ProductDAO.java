package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dao.CrudDAO;
import lk.ijse.gdse71.finalproject.dto.ContainDto;
import lk.ijse.gdse71.finalproject.dto.ProductDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDAO extends CrudDAO {

     ArrayList<ProductDto> getAllProducts() throws SQLException;

     String getNextProduct() throws SQLException;

     boolean deleteProduct(String productId) throws SQLException ;

     boolean saveProduct(ProductDto productDto) throws SQLException ;

     boolean updateProduct(ProductDto productDto) throws SQLException ;

     ProductDto findByPid(String selectedProId) throws SQLException ;

     boolean reduceQty(ContainDto containDto) throws SQLException;

     ArrayList<String> getAllProductids() throws SQLException;
}
