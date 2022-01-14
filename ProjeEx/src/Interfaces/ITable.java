package Interfaces;

import java.sql.SQLException;

public interface ITable {
    public void fillTable() throws SQLException;
    public void updateTableModel() throws SQLException;
}
