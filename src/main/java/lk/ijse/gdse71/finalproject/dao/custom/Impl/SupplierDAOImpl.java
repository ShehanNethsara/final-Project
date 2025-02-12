package lk.ijse.gdse71.finalproject.dao.custom.Impl;

import lk.ijse.gdse71.finalproject.dao.custom.SupplierDAO;
import lk.ijse.gdse71.finalproject.dto.SupplierDto;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {

    public ArrayList<SupplierDto> getAll() throws SQLException{
        ResultSet rst = CrudUtil.execute("select * from supplier");
        ArrayList<SupplierDto> supplierDtos = new ArrayList<>();

        while(rst.next()){
            SupplierDto supplierDto = new SupplierDto(
                    rst.getString(1),
                    rst.getString(3),
                    rst.getString(2),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            supplierDtos.add(supplierDto);
        }
        return supplierDtos;
    }

    public String getNext() throws SQLException {
        ResultSet rst = CrudUtil.execute("select supplier_id from supplier order by supplier_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("S%03d", newIdIndex);
        }
        return "S001";
    }

    public boolean deleted(String supplierId) throws SQLException {
        return CrudUtil.execute("delete from supplier where supplier_id=?", supplierId);
    }

    public boolean update(SupplierDto supplierDto) throws SQLException {
        return CrudUtil.execute(
                "update supplier set supplier_name=?, address=?, company=?, email=?, contact_number=? where supplier_id=?",

                supplierDto.getName(),
                supplierDto.getAddress(),
                supplierDto.getCompany(),
                supplierDto.getEmail(),
                supplierDto.getContact(),
                supplierDto.getId()
        );
    }

    public boolean save(SupplierDto supplierDto) throws SQLException {
        return CrudUtil.execute(
                "insert into supplier values (?,?,?,?,?,?)",
                 supplierDto.getId(),
                 supplierDto.getAddress(),
                supplierDto.getName(),
                supplierDto.getCompany(),
                 supplierDto.getEmail(),
                 supplierDto.getContact()
        );
    }

}
