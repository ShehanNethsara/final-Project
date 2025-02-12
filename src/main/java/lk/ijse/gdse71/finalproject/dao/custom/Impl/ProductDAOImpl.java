package lk.ijse.gdse71.finalproject.dao.custom.Impl;

import lk.ijse.gdse71.finalproject.dao.custom.ProductDAO;
import lk.ijse.gdse71.finalproject.dto.ContainDto;
import lk.ijse.gdse71.finalproject.dto.ProductDto;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAOImpl implements ProductDAO {

  public ArrayList<ProductDto> getAll() throws SQLException {
      ResultSet rst = CrudUtil.execute("select * from product");

      ArrayList<ProductDto> productDtos = new ArrayList<>();

      while (rst.next()) {
          ProductDto productDto = new ProductDto(
                  rst.getString(1),
                  rst.getString(2),
                  rst.getString(3),
                 rst.getString(4)

          );
          productDtos.add(productDto);
      }
      return productDtos;
  }

    public String getNext() throws SQLException {
        ResultSet rst =CrudUtil.execute("select product_id from product order by product_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }
        return "P001";
    }
    public boolean delete(String productId) throws SQLException {
        return CrudUtil.execute("delete from product where product_id=?", productId);

    }

    public boolean save(ProductDto productDto) throws SQLException {
      return CrudUtil.execute(
              "insert into product values (?,?,?,?)",
                productDto.getId(),
                productDto.getName(),
                productDto.getPrice(),
                productDto.getQty()

      );
    }

    public boolean update(ProductDto productDto) throws SQLException {
      return CrudUtil.execute(
              "update product set  product_name=?, product_price=?, product_qty=? where product_id=?",
              productDto.getName(),
              productDto.getPrice(),
              productDto.getQty(),
              productDto.getId()

      );

    }

    public ProductDto findByPid(String selectedProId) throws SQLException {

        ResultSet rst = CrudUtil.execute("select * from product where product_id=?", selectedProId);

        if (rst.next()) {
            return new ProductDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
        }

        return null;
    }

    public boolean reduceQty(ContainDto containDto) throws SQLException {
      return CrudUtil.execute("UPDATE product SET product_qty = product_qty-? WHERE product_id = ?",
              containDto.getQty(),
              containDto.getProductId()
              );
    }

    public ArrayList<ProductDto> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select product_id from product");

        ArrayList<String> productIds = new ArrayList<>();

        while (rst.next()) {
            productIds.add(rst.getString(1));
        }
        return productIds;
    }
}
