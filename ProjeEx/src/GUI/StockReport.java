package GUI;

import Interfaces.IFrame;
import com.helper.Helper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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

public class StockReport extends JDialog implements IFrame {

    public StockReport() throws HeadlessException, SQLException {
        initComponents();
    }

    public void fillTable() throws SQLException {
        DefaultTableModel tableModel = new DefaultTableModel();
        Object[]column = new Object[4];
        column[0] = "Id";
        column[1] = "Product Id";
        column[2] = "Price";
        column[3] = "Amount";
        tableModel.setColumnIdentifiers(column);
        ArrayList<Stock> stocks = new ArrayList<>();
        stocks = new Stock().getAll();
        String data[] = new String[4];

        for(int i = 0; i < stocks.size(); i++){
            data[0] = String.valueOf(stocks.get(i).getId());
            data[1] = String.valueOf(stocks.get(i).getProductId());
            data[2] = String.valueOf(stocks.get(i).getPrice());
            data[3] = String.valueOf(stocks.get(i).getAmount());
            tableModel.addRow(data);
        }
        JTable table_stock = new JTable(tableModel);
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
        scrollPane.setBounds(30, 195, 645, 390);
        this.add(scrollPane);

    }

    public void pdfGenerate(){
        String path = "";
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = jFileChooser.showSaveDialog(StockReport.this);

        if(x == JFileChooser.APPROVE_OPTION){
            path = jFileChooser.getSelectedFile().getPath();
        }

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(path + "stockReport.pdf"));
            document.open();
            PdfPTable table = new PdfPTable(4);

            //Tablo başlıklarını ekledik.
            table.addCell("Id");
            table.addCell("Product Name");
            table.addCell("Price");
            table.addCell("Amount");

            Connection connection = Helper.getConnection();
            String query = "select Table_Stock.Id, p_name, price, amount from Table_Stock\n" +
                    "Inner join Table_Product on Table_Stock.productId = Table_Product.Id";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                table.addCell(String.valueOf(resultSet.getInt("Id")));
                table.addCell(resultSet.getString("p_name"));
                table.addCell(String.valueOf(resultSet.getDouble("price")));
                table.addCell(String.valueOf(resultSet.getInt("amount")));
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
        backgroundImage.setIcon(Helper.createImage("src//javaProjeImage//stockReport.png"));


        this.add(btnGeneratePdf);
        this.add(backgroundImage);

        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setResizable(false);
        this.setSize(720, 650);
        this.setTitle("Stock Report");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
