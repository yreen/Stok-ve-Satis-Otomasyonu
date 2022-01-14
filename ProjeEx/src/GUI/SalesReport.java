package GUI;

import Interfaces.IFrame;
import Interfaces.ITable;
import com.helper.Helper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.SellProduct;
import model.Stock;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SalesReport extends JDialog implements IFrame, ITable {

    public SalesReport() throws HeadlessException, SQLException {

        initComponents();
    }
    @Override
    public void fillTable() throws SQLException {
        DefaultTableModel tableModel = new DefaultTableModel();
        Object[]column = new Object[6];
        column[0] = "Id";
        column[1] = "Stock Id";
        column[2] = "Product Id";
        column[3] = "Price";
        column[4] = "Amount";
        column[5] = "Customer Id";
        tableModel.setColumnIdentifiers(column);
        ArrayList<SellProduct> sellProducts = new SellProduct().getAllSoldProducts();
        String data[] = new String[6];

        for(int i = 0; i < sellProducts.size(); i++){
            data[0] = String.valueOf(sellProducts.get(i).getId());
            data[1] = String.valueOf(sellProducts.get(i).getStockId());
            data[2] = String.valueOf(sellProducts.get(i).getProductId());
            data[3] = String.valueOf(sellProducts.get(i).getPrice());
            data[4] = String.valueOf(sellProducts.get(i).getAmount());
            data[5] = String.valueOf(sellProducts.get(i).getCustomerId());
            tableModel.addRow(data);
        }
        JTable table_sell = new JTable(tableModel);
        table_sell.setRowHeight(25);
        table_sell.setDefaultEditor(Object.class, null);
        table_sell.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table_sell.setSelectionBackground(Helper.getColor(1));
        table_sell.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table_sell.getTableHeader().setOpaque(false);
        table_sell.getTableHeader().setBackground(Helper.getColor(6));
        table_sell.setShowVerticalLines(false);
        table_sell.getTableHeader().setBorder(null);
        table_sell.setFont(new Font("Segoe UI", Font.BOLD, 15));
        table_sell.setFocusable(false);
        table_sell.setShowGrid(false);
        table_sell.setIntercellSpacing(new Dimension(0,0));
        table_sell.getTableHeader().setForeground(Color.white);

        JScrollPane scrollPane = new JScrollPane(table_sell, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(30, 195, 650, 390);
        this.add(scrollPane);

    }

    @Override
    public void updateTableModel() throws SQLException {

    }

    public void pdfGenerate(){
        String path = "";
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = jFileChooser.showSaveDialog(SalesReport.this);

        if(x == JFileChooser.APPROVE_OPTION){
            path = jFileChooser.getSelectedFile().getPath();
        }

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(path + "saleReport.pdf"));
            document.open();
            PdfPTable table = new PdfPTable(6);

            //Tablo başlıklarını ekledik.
            table.addCell("Id");
            table.addCell("Stock Id");
            table.addCell("Product Name");
            table.addCell("Price");
            table.addCell("Amount");
            table.addCell("Customer Name");

            Connection connection = Helper.getConnection();
            String query = "select Table_Sell.Id, stockId, Table_Product.p_name, price, amount, (Table_Customer.name + ' ' + Table_Customer.surname) as c_name from Table_Sell\n" +
                    "Inner Join Table_Product on Table_Sell.productId = Table_Product.Id\n" +
                    "Inner Join Table_Customer on Table_Sell.customerId = Table_Customer.Id";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                table.addCell(String.valueOf(resultSet.getInt("Id")));
                table.addCell(String.valueOf(resultSet.getInt("stockId")));
                table.addCell(resultSet.getString("p_name"));
                table.addCell(String.valueOf(resultSet.getDouble("price")));
                table.addCell(String.valueOf(resultSet.getInt("amount")));
                table.addCell(String.valueOf(resultSet.getString("c_name")));
            }
            document.add(table);

        } catch (DocumentException | FileNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }

        document.close();

    }

    @Override
    public void initComponents() throws SQLException {
        fillTable();

        JButton btnGeneratePdf = new JButton("Generate PDF");
        btnGeneratePdf.setBounds(30,100 ,150,50);
        btnGeneratePdf.setFont(Helper.write(15, 1));
        btnGeneratePdf.setFocusable(false);
        btnGeneratePdf.setIcon(Helper.createImage("src/javaProjeImage/btnGenerate.png"));
        btnGeneratePdf.setVerticalTextPosition(JButton.CENTER);
        btnGeneratePdf.setHorizontalTextPosition(JButton.RIGHT);
        btnGeneratePdf.setBackground(Color.white);
        btnGeneratePdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdfGenerate();
            }
        });

        JLabel backgroundImage = new JLabel();
        backgroundImage.setIcon(Helper.createImage("src//javaProjeImage//salesReport.png"));

        this.add(btnGeneratePdf);
        this.add(backgroundImage);

        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
        this.setResizable(false);
        this.setSize(720, 650);
        this.setTitle("Stock Report");
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
}
