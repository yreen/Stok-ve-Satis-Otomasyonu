package GUI;

import Interfaces.IFrame;
import com.helper.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StockJoinTable extends JDialog implements IFrame {
    private DefaultTableModel tableModel;
    private JTable table_stock;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public StockJoinTable() throws SQLException {
        initComponents();
    }

    @Override
    public void initComponents() throws SQLException {
        getInnerJoin();
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setTitle("Table");
        this.setResizable(false);
        this.setSize(600,400);
        this.setVisible(true);

    }

    public void getInnerJoin() throws SQLException {
        try {
            tableModel = new DefaultTableModel();
            Object[] column = {"Id", "Product Name", "Price", "Amount"};
            tableModel.setColumnIdentifiers(column);
            Object data[] = new Object[4];
            connection = Helper.getConnection();
            String query = "select Table_Stock.Id, p_name, price, amount from Table_Stock\n" +
                    "Inner join Table_Product on Table_Stock.productId = Table_Product.Id";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                data[0] = resultSet.getInt("Id");
                data[1] = resultSet.getString("p_name");
                data[2] = resultSet.getString("price");
                data[3] = resultSet.getString("amount");
                tableModel.addRow(data);
            }

            table_stock = new JTable(tableModel);
            table_stock = new JTable(tableModel);
            table_stock.setRowHeight(25);
            table_stock.setDefaultEditor(Object.class, null);
            table_stock.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            table_stock.setSelectionBackground(Helper.getColor(1));
            table_stock.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
            table_stock.getTableHeader().setOpaque(false);
            table_stock.getTableHeader().setBackground(Helper.getColor(6));
            table_stock.setShowVerticalLines(false);
            table_stock.getTableHeader().setBorder(null);
            table_stock.setFont(new Font("Segoe UI", Font.BOLD, 15));
            table_stock.setFocusable(false);
            table_stock.setShowGrid(false);
            table_stock.setIntercellSpacing(new Dimension(0,0));



            JScrollPane scrollPane = new JScrollPane(table_stock, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            this.add(scrollPane);


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
            statement.close();
            resultSet.close();
        }

    }
}
