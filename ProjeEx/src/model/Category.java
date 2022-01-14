package model;

import Interfaces.IDataAcces;
import com.helper.Helper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Category implements IDataAcces<Category> {
    private int id;
    private String  name;

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public void Insert(Category entity) throws SQLException {
        try {
            connection = Helper.getConnection();
            String query = "INSERT INTO Table_Category (categoryName) VALUES ('"+entity.getName()+"') ";
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
        }

    }

    @Override
    public ArrayList<Category> getAll() throws SQLException {
        ArrayList<Category> categories = new ArrayList<>();

        Category category;
        try {
            connection = Helper.getConnection();
            String query = "SELECT * FROM Table_Category";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                category = new Category();
                category.setId(resultSet.getInt("Id"));
                category.setName(resultSet.getString("categoryName"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
        }
        return categories;
    }

    @Override
    public void delete(int id) throws SQLException {
        try {
            connection = Helper.getConnection();
            String query = "DELETE FROM Table_Category WHERE Id = '"+id+"'";
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
        }

    }

    @Override
    public void Update(Category entity) throws SQLException {
        try {
            connection = Helper.getConnection();
            String query = "UPDATE Table_Category SET categoryName = ? WHERE Id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            preparedStatement.close();
        }

    }

    @Override
    public List<Category> getById(int id) {
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
