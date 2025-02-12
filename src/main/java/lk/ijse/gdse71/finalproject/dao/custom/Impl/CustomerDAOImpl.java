package lk.ijse.gdse71.finalproject.dao.custom.Impl;

import lk.ijse.gdse71.finalproject.dao.custom.CustomerDAO;
import lk.ijse.gdse71.finalproject.dto.CustomerDto;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

   public class CustomerDAOImpl implements CustomerDAO {

    public ArrayList<CustomerDto> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer");

        ArrayList<CustomerDto> customerDTOS = new ArrayList<>();

        while (rst.next()) {
            CustomerDto customerDto = new CustomerDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            customerDTOS.add(customerDto);
        }
        return customerDTOS;
    }

    public boolean save(CustomerDto customerDto) throws SQLException {
        return CrudUtil.execute(
                "insert into customer values (?,?,?,?,?)",
                customerDto.getCustomerId(),
                customerDto.getCustomerName(),
                customerDto.getNic(),
                customerDto.getEmail(),
                customerDto.getPhone()
        );
    }

    public boolean delete(String customerId) throws SQLException {
        return CrudUtil.execute("delete from customer where customer_id=?", customerId);

    }

    public boolean update(CustomerDto customerDto) throws SQLException {
        return CrudUtil.execute(
                "update customer set name=?, nic=?, email=?, phone=? where customer_id=?",
                customerDto.getCustomerName(),
                customerDto.getNic(),
                customerDto.getEmail(),
                customerDto.getPhone(),
                customerDto.getCustomerId()
        );
    }

    public String getNext() throws SQLException {
        ResultSet rst = CrudUtil.execute("select customer_id from customer order by customer_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("C%03d", newIdIndex); // Return the new customer ID
        }
        return "C001";
    }

}