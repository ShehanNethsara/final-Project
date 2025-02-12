package lk.ijse.gdse71.finalproject.dao.custom.Impl;

import lk.ijse.gdse71.finalproject.dao.custom.InventaryDAO;
import lk.ijse.gdse71.finalproject.db.DBConnection;
import lk.ijse.gdse71.finalproject.dto.InventaryDto;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventaryDAOImpl implements InventaryDAO {
    public String getNext() throws SQLException {
        ResultSet rst =CrudUtil.execute("select inventory_id from inventory order by inventory_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("I%03d", newIdIndex);
        }
        return "I001";
    }

    public ArrayList<InventaryDto> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from inventory");
        ArrayList<InventaryDto> inventaryDtos = new ArrayList<>();

        while (rst.next()) {
            InventaryDto inventaryDto  = new InventaryDto(
                    rst.getString(5),
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(4),
                    rst.getString(3)

            );
            inventaryDtos.add(inventaryDto);
        }
        return inventaryDtos;
    }

    public boolean delete(String employeeId) throws SQLException {
        return CrudUtil.execute("delete from inventory where inventory_id=?", employeeId);

    }

    public boolean save(InventaryDto inventaryDto, String supplierId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            int qty = Integer.parseInt(inventaryDto.getQty());

            boolean isSaved = CrudUtil.execute(
                    "INSERT INTO inventory (inventory_id, inventory_name, description, qty, employee_id) VALUES (?, ?, ?, ?, ?)",
                    inventaryDto.getInventaryId(),
                    inventaryDto.getName(),
                    inventaryDto.getDescription(),
                    qty,
                    inventaryDto.getEmployeeId()
            );

            if (isSaved) {
                // Save inventory ID and supplier ID in SupplierDetailsModel
                boolean isSupplierSaved = SupplierDetailsDAOImpl.save(inventaryDto.getInventaryId(), supplierId);

                if (isSupplierSaved) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    System.err.println("Failed to save supplier details.");
                    return false;
                }
            } else {
                connection.rollback();
                System.err.println("Failed to save inventory.");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }




    public boolean update(InventaryDto inventaryDto) throws SQLException {

        int qty = Integer.parseInt(inventaryDto.getQty());

        return CrudUtil.execute(
                "UPDATE inventory SET employee_id=?, inventory_name=?, qty=? , description=?  WHERE inventory_id=?",
                inventaryDto.getEmployeeId(),
                inventaryDto.getName(),
                qty,
                inventaryDto.getDescription(),
                inventaryDto.getInventaryId()

        );
    }

}
