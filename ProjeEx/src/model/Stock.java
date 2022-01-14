package model;

import Interfaces.IDataAcces;
import com.helper.Helper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Stock implements IDataAcces<Stock> {
    private int id;
    private int productId;
    private double price;
    private int amount;

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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    @Override
    public void Insert(Stock entity) throws SQLException {
        try {
            connection = Helper.getConnection();
            String query = "INSERT INTO Table_Stock (productId, price, amount) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, entity.getProductId());
            preparedStatement.setDouble(2, entity.getPrice());
            preparedStatement.setInt(3, entity.getAmount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            preparedStatement.close();
        }

    }

    @Override
    public ArrayList<Stock> getAll() throws SQLException {
        ArrayList<Stock> stocks = new ArrayList<>();

        Stock stock;
        try {
            connection = Helper.getConnection();
            String query = "SELECT * FROM Table_Stock";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                stock = new Stock();
                stock.setId(resultSet.getInt("Id"));
                stock.setProductId(resultSet.getInt("productId"));
                stock.setPrice(resultSet.getDouble("price"));
                stock.setAmount(resultSet.getInt("amount"));
                stocks.add(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
        }
        return stocks;
    }

    @Override
    public void delete(int id) throws SQLException {
        try {
            connection = Helper.getConnection();
            String query = "DELETE FROM Table_Stock WHERE Id = '"+id+"'";
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
        }

    }

    public Stock searchStock(int id) throws SQLException {
        Stock stock = null;
        try {
            connection = Helper.getConnection();
            String query = "SELECT * FROM Table_Stock WHERE Id = '"+id+"'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                stock = new Stock();
                stock.setId(resultSet.getInt("Id"));
                stock.setProductId(resultSet.getInt("productId"));
                stock.setPrice(resultSet.getDouble("price"));
                stock.setAmount(resultSet.getInt("amount"));

            }
            if(resultSet.next()){
                return  null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
        }
        return stock;

    }

    public ArrayList<Stock> searchStockByProductId(int id) throws SQLException {
        ArrayList<Stock> stocks = new ArrayList<>();

        Stock stock;
        try {
            connection = Helper.getConnection();
            String query = "SELECT * FROM Table_Stock WHERE productId = '"+id+"'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){

                stock = new Stock();
                stock.setId(resultSet.getInt("Id"));
                stock.setProductId(resultSet.getInt("productId"));
                stock.setPrice(resultSet.getDouble("price"));
                stock.setAmount(resultSet.getInt("amount"));
                stocks.add(stock);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
        }
        return stocks;
    }

    public Boolean searchStockByProduct(int id) throws SQLException {
        try {
            connection = Helper.getConnection();
            String query = "SELECT * FROM Table_Stock WHERE productId = '"+id+"'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
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


    public void decreaseStock(Stock entity) throws SQLException {
        try {
            Stock temp = new Stock().searchStock(entity.getId());
            connection = Helper.getConnection();
            String query = "UPDATE Table_Stock SET amount = ? WHERE Id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ( temp.getAmount()- entity.getAmount()));
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
    public void Update(Stock entity) throws SQLException {
        try {
            connection = Helper.getConnection();
            String query = "UPDATE Table_Stock SET productId = ?, price = ?, amount = ? WHERE Id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, entity.getProductId());
            preparedStatement.setDouble(2, entity.getPrice());
            preparedStatement.setInt(3, entity.getAmount());
            preparedStatement.setInt(4, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            preparedStatement.close();
        }

    }

    @Override
    public List<Stock> getById(int id) {
        return null;
    }

    public int totalStock() throws SQLException {
        int toplam = 0;
        try {
            connection = Helper.getConnection();
            String query = "select sum(amount)as 'Total' from Table_Stock";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                toplam = resultSet.getInt("Total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
        }
        return toplam;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", productId=" + productId +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
