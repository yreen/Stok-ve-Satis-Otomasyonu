package Interfaces;

import java.sql.*;
import java.util.List;

public interface IDataAcces<T> {

    public void Insert(T entity) throws SQLException;
    public List<T> getAll() throws SQLException;
    public void delete(int id) throws SQLException;
    public void Update(T entity) throws SQLException;
    public List<T> getById(int id);

}
