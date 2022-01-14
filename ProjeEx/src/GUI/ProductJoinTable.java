package GUI;

import Interfaces.IFrame;
import com.helper.Helper;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductJoinTable extends JDialog implements IFrame {
    private DefaultTableModel tableModel;
    private JTable table_product;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public ProductJoinTable() throws SQLException {

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

    public void initJTable(){



    }

    public void getInnerJoin() throws SQLException {
        try {
            tableModel = new DefaultTableModel();
            Object[] column = {"Id", "Category Name", "Product Name", "Brand Name", "Explanation"};
            tableModel.setColumnIdentifiers(column);
            Object data[] = new Object[5];
            connection = Helper.getConnection();
            String query = "select Table_Product.Id, categoryName, p_name, brandName, explanation from Table_Product\n" +
                    "Inner join Table_Category on Table_Product.categoryId = Table_Category.Id\n" +
                    "Inner join Table_Brand on Table_Product.brandId = Table_Brand.Id";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                data[0] = resultSet.getInt("Id");
                data[1] = resultSet.getString("categoryName");
                data[2] = resultSet.getString("p_name");
                data[3] = resultSet.getString("brandName");
                data[4] = resultSet.getString("explanation");
                tableModel.addRow(data);
            }

            table_product = new JTable(tableModel);
            table_product = new JTable(tableModel);
            table_product.setRowHeight(25);
            table_product.setDefaultEditor(Object.class, null);
            table_product.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            table_product.setSelectionBackground(Helper.getColor(1));
            table_product.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
            table_product.getTableHeader().setOpaque(false);
            table_product.getTableHeader().setBackground(Helper.getColor(6));
            table_product.setShowVerticalLines(false);
            table_product.getTableHeader().setBorder(null);
            table_product.setFont(new Font("Segoe UI", Font.BOLD, 15));
            table_product.setFocusable(false);
            table_product.setShowGrid(false);
            table_product.setIntercellSpacing(new Dimension(0,0));



            JScrollPane scrollPane = new JScrollPane(table_product, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
