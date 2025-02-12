package lk.ijse.gdse71.finalproject.dao;

import lk.ijse.gdse71.finalproject.dto.CustomerDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO <T> {

    ArrayList<T> getAll() throws SQLException;


    boolean save(T Dto) throws SQLException;


    boolean delete(String Id) throws SQLException ;

    boolean update( T Dto) throws SQLException;

    String getNext() throws SQLException ;

}
