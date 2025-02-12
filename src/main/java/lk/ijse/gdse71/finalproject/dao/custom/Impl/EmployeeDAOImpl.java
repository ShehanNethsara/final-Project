package lk.ijse.gdse71.finalproject.dao.custom.Impl;

import lk.ijse.gdse71.finalproject.dao.custom.EmployeeDAO;
import lk.ijse.gdse71.finalproject.dto.CustomerDto;
import lk.ijse.gdse71.finalproject.dto.EmployeeDto;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    public  boolean save(EmployeeDto employeeDto) throws SQLException {

        return CrudUtil.execute(
                "insert into employee values (?,?,?,?,?,?)",
                employeeDto.getEmployeeId(),
                employeeDto.getEmployeeName(),
                employeeDto.getAddress(),
                employeeDto.getSalary(),
                employeeDto.getJobRoll(),
                employeeDto.getContactNumber()
        );
    }

    public  boolean update(EmployeeDto employeeDto) throws SQLException {

        return CrudUtil.execute(
                "update employee set name = ?, address = ?, salary = ?, jobroll=?, contact_number=? where employee_id=?",

                employeeDto.getEmployeeName(),
                employeeDto.getAddress(),
                employeeDto.getSalary(),
                employeeDto.getJobRoll(),
                employeeDto.getContactNumber(),
                employeeDto.getEmployeeId()

        );
    }

    public String getNext() throws SQLException {
            ResultSet rst =CrudUtil.execute("select employee_id from employee order by employee_id desc limit 1");

            if (rst.next()) {
                String lastId = rst.getString(1);
                String substring = lastId.substring(1);
                int i = Integer.parseInt(substring);
                int newIdIndex = i + 1;
                return String.format("E%03d", newIdIndex);
            }
            return "E001";
    }

    public ArrayList<EmployeeDto> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from employee");
        ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();

        while (rst.next()) {
            EmployeeDto employeeDto = new EmployeeDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            employeeDtos.add(employeeDto);
        }

        return employeeDtos;
    }

    public boolean delete(String employeeId) throws SQLException {
        return CrudUtil.execute("delete from employee where employee_id=?", employeeId);
    }

}
