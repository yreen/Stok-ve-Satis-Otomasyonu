package model;

import Interfaces.IDataAcces;
import com.helper.Helper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Brand implements IDataAcces<Brand> {
    private int id;
    private String brandName;

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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public ArrayList<Brand> getBrandNames() throws SQLException {
        ArrayList<Brand> brands = new ArrayList<>();

        Brand brand;
        try {
            connection = Helper.getConnection();
            String query = "SELECT * FROM Table_Brand";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                brand = new Brand();
                brand.setId(resultSet.getInt("Id"));
                brand.setBrandName(resultSet.getString("brandName"));
                brands.add(brand);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
        }
        return brands;

    }


    @Override
    public void Insert(Brand entity) throws SQLException {
        try {
            connection = Helper.getConnection();
            String query = "INSERT INTO Table_Brand (brandName) VALUES ('"+entity.getBrandName()+"')";
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
    public ArrayList<Brand> getAll() throws SQLException {
        ArrayList<Brand> brands = new ArrayList<>();
        Brand brand = null;
        try {
            connection = Helper.getConnection();
            String query = "SELECT * FROM Table_Brand";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                brand = new Brand();
                brand.setId(resultSet.getInt("Id"));
                brand.setBrandName(resultSet.getString("brandName"));
                brands.add(brand);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
        }
        return brands;
    }

    @Override
    public void delete(int id) throws SQLException {
        try {
            connection = Helper.getConnection();
            String query = "DELETE FROM Table_Brand WHERE Id = '"+id+"'";
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
    public void Update(Brand entity) throws SQLException {
        try {
            connection = Helper.getConnection();
            String query = "UPDATE Table_Brand SET brandName = ? WHERE Id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, entity.getBrandName());
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
    public List<Brand> getById(int id) {
        return null;
    }

    @Override
    public String toString() {
        return brandName;
    }
}
