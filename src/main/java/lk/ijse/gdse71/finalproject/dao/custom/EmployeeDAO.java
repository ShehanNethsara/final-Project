package lk.ijse.gdse71.finalproject.dao.custom;

import lk.ijse.gdse71.finalproject.dao.CrudDAO;
import lk.ijse.gdse71.finalproject.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeDAO extends CrudDAO {

  boolean saveEmployee(EmployeeDto employeeDto) throws SQLException;

  boolean updateEmployee(EmployeeDto employeeDto) throws SQLException ;

    String getNextEmployee() throws SQLException ;

    ArrayList<EmployeeDto> getAllEmployee() throws SQLException;

    boolean deleteEmployee(String employeeId) throws SQLException;
}
