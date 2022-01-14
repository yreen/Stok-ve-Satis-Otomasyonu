package model;

import Interfaces.IDataAcces;
import com.helper.Helper;

import javax.sound.sampled.Port;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Product implements IDataAcces<Product> {
    private int Id;
    private int categoryId;
    private String name;
    private int brandId;
    private String explanation;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }


    @Override
    public void Insert(Product entity) throws SQLException {
        try {
            connection = Helper.getConnection();
            String query = "INSERT INTO Table_Product (categoryId, p_name, brandId, explanation) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, entity.getCategoryId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setInt(3, entity.getBrandId());
            preparedStatement.setString(4, entity.getExplanation());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            preparedStatement.close();
        }

    }

    @Override
    public ArrayList<Product> getAll() throws SQLException {
        ArrayList<Product> data = new ArrayList<>();

        Product product;
        try {
            connection = Helper.getConnection();
            String query = "SELECT * FROM Table_Product";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                product = new Product();
                product.setId(resultSet.getInt("Id"));
                product.setCategoryId(resultSet.getInt("categoryId"));
                product.setName(resultSet.getString("p_name"));
                product.setBrandId(resultSet.getInt("brandId"));
                product.setExplanation(resultSet.getString("explanation"));
                data.add(product);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
        }

        return data;
    }

    @Override
    public void delete(int id) throws SQLException {
        try {
            connection = Helper.getConnection();
            String query = "DELETE FROM Table_Product WHERE Id = '"+id+"'";
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
        }


    }

    public Product searchProduct(int id) throws SQLException {
        Product product = null;
        try {
            connection = Helper.getConnection();
            String query = "SELECT * FROM Table_Product WHERE Id = '"+id+"'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                product = new Product();
                product.setId(resultSet.getInt("Id"));
                product.setCategoryId(resultSet.getInt("categoryId"));
                product.setName(resultSet.getString("p_name"));
                product.setBrandId(resultSet.getInt("brandId"));
                product.setExplanation(resultSet.getString("explanation"));
            }
            if(resultSet.next()){
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
        }
        return product;

    }

    @Override
    public void Update(Product entity) throws SQLException {
        try {
            connection = Helper.getConnection();
            String query = "UPDATE Table_Product SET categoryId = ?, p_name = ?, brandId = ?, explanation = ? WHERE Id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, entity.getCategoryId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setInt(3, entity.getBrandId());
            preparedStatement.setString(4, entity.getExplanation());
            preparedStatement.setInt(5, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            preparedStatement.close();
        }


    }

    @Override
    public List<Product> getById(int id) {
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}


