package model;

import Interfaces.IDataAcces;
import com.helper.Helper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Customer implements IDataAcces<Customer> {
    private int id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String accountNumber;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }



    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    @Override
    public void Insert(Customer entity) throws SQLException {
        try {
            connection = Helper.getConnection();
            String query = "INSERT INTO Table_Customer (name, surname, phone_number, email, account_number) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            preparedStatement.setString(3, entity.getPhoneNumber());
            preparedStatement.setString(4, entity.getEmail());
            preparedStatement.setString(5, entity.getAccountNumber());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            preparedStatement.close();
        }
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException {
        ArrayList<Customer> data = new ArrayList<>();

            Customer customer;
        try {
            connection = Helper.getConnection();
            String query = "SELECT * FROM Table_Customer";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                customer = new Customer();
                customer.setId(resultSet.getInt("Id"));
                customer.setName(resultSet.getString("name"));
                customer.setSurname(resultSet.getString("surname"));
                customer.setPhoneNumber(resultSet.getString("phone_number"));
                customer.setEmail(resultSet.getString("email"));
                customer.setAccountNumber(resultSet.getString("account_number"));
                data.add(customer);

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
            String query = "DELETE FROM Table_Customer WHERE Id = '"+id+"'";
            statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
        }

    }

    public Customer searchCustomer(int id) throws SQLException {
        Customer customer = null;
        try {
            connection = Helper.getConnection();
            String query = "SELECT * FROM Table_Customer where Id = '"+id+"'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                customer = new Customer();
                customer.setId(resultSet.getInt("Id"));
                customer.setName(resultSet.getString("name"));
                customer.setSurname(resultSet.getString("surname"));
                customer.setPhoneNumber(resultSet.getString("phone_number"));
                customer.setEmail(resultSet.getString("email"));
                customer.setAccountNumber(resultSet.getString("account_number"));

            }
            if(resultSet.next()){
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
        }
        return customer;
    }

    @Override
    public void Update(Customer entity) throws SQLException {
        try {
            connection = Helper.getConnection();
            String query = "UPDATE Table_Customer SET name = ?, surname = ?, phone_number = ?, email = ?, account_number = ? WHERE Id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            preparedStatement.setString(3, entity.getPhoneNumber());
            preparedStatement.setString(4, entity.getEmail());
            preparedStatement.setString(5, entity.getAccountNumber());
            preparedStatement.setInt(6, entity.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            preparedStatement.close();
        }

    }

    @Override
    public List<Customer> getById(int id) {
        return null;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
