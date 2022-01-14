package model;

import Interfaces.IDataAcces;
import com.helper.Helper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class User implements IDataAcces<User> {
    private int id;
    private String username;
    private String password;
    private int authorityId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(int authorityId) {
        this.authorityId = authorityId;
    }

    @Override
    public void Insert(User entity) throws SQLException {
        try {
            connection = Helper.getConnection();
            String query = "INSERT INTO Table_User (username, password, authorityId) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setInt(3, entity.getAuthorityId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            preparedStatement.close();
        }


    }

    @Override
    public ArrayList<User> getAll() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        User user;
        connection = Helper.getConnection();
        String query = "SELECT * FROM Table_User";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            user = new User();
            user.setId(resultSet.getInt("Id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setAuthorityId(resultSet.getInt("authorityId"));
            users.add(user);
        }
        return users;
    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public void Update(User entity) throws SQLException {

    }

    @Override
    public List<User> getById(int id) {
        return null;
    }

    public boolean checkUser(User entity) throws SQLException {
        try {
            connection = Helper.getConnection();
            String query = "SELECT * FROM Table_User Where username = '"+entity.getUsername()+"' and password = '"+entity.getPassword()+"'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
        }
        return false;

    }

    public int getAuthority(User user){
    return 0;
    }

    public User searchUser(String username, String password) throws SQLException {
        User user = null;
        try {
            connection = Helper.getConnection();
            String query = "SELECT * FROM Table_User where username = '"+username+"' and password = '"+password+"'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("Id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setAuthorityId(resultSet.getInt("authorityId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
        }
        return user;

    }
}
