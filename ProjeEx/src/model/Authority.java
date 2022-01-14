package model;

import Interfaces.IDataAcces;
import com.helper.Helper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Authority implements IDataAcces<Authority> {
    private int id;
    private String authorityName;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }


    @Override
    public void Insert(Authority entity) throws SQLException {

    }

    @Override
    public ArrayList<Authority> getAll() throws SQLException {
        ArrayList<Authority> authorities = new ArrayList<>();
        Authority authority = new Authority();
        try {
            connection = Helper.getConnection();
            String query = "SELECT * FROM Table_Authority";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                authority = new Authority();
                authority.setId(resultSet.getInt("Id"));
                authority.setAuthorityName(resultSet.getString("authorityName"));
                authorities.add(authority);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
        }
        return authorities;

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public void Update(Authority entity) throws SQLException {

    }

    @Override
    public List<Authority> getById(int id) {
        return null;
    }

    @Override
    public String toString() {
        return authorityName;
    }
}
