package model;


import com.helper.Helper;

import java.sql.*;
import java.util.ArrayList;


public class SellProduct extends Stock{
    private int id;
    private int stockId;
//    private int productId;
//    private int amount;
//    private double price;
    private int customerId;

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

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }




    public void Insert(SellProduct entity) throws SQLException {
        try {
            connection = Helper.getConnection();
            String query = "INSERT INTO Table_Sell (stockId, productId, price, amount, customerId) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, entity.getStockId());
            preparedStatement.setInt(2,entity.getProductId());
            preparedStatement.setDouble(3, entity.getPrice());
            preparedStatement.setInt(4, entity.getAmount());
            preparedStatement.setInt(5, entity.getCustomerId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            preparedStatement.close();
        }

    }


    public ArrayList<SellProduct> getAllSoldProducts() throws SQLException {
        ArrayList<SellProduct> sellProducts = new ArrayList<>();
        SellProduct sellProduct;
        connection = Helper.getConnection();;
        String query = "SELECT * FROM Table_Sell";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            sellProduct = new SellProduct();
            sellProduct.setId(resultSet.getInt("Id"));
            sellProduct.setStockId(resultSet.getInt("stockId"));
            sellProduct.setProductId(resultSet.getInt("productId"));
            sellProduct.setPrice(resultSet.getDouble("price"));
            sellProduct.setAmount(resultSet.getInt("amount"));
            sellProduct.setCustomerId(resultSet.getInt("customerId"));
            sellProducts.add(sellProduct);
        }
        return sellProducts;
    }


    public void delete(int id) throws SQLException {

    }


    public void Update(SellProduct entity) throws SQLException {

    }

    public double totalQuantity() throws SQLException {
        double total = 0;
        try {
            connection = Helper.getConnection();
            String query = "select sum(Table_Sell.amount*Table_Sell.price) as total from Table_Sell";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                total = resultSet.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
        }
        return total;
    }


}
