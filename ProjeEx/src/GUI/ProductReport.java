package GUI;

import Interfaces.IFrame;
import com.helper.Helper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.Customer;
import model.Product;

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

public class ProductReport extends JDialog implements IFrame {

    public ProductReport() throws HeadlessException, SQLException {
        initComponents();

    }

    public void fillTable() throws SQLException {
        DefaultTableModel tableModel = new DefaultTableModel();
        Object[] column = new Object[5];
        column[0] = "Id";
        column[1] = "Category Id";
        column[2] = "Product Nmae";
        column[3] = "Brand Id";
        column[4] = "Explanation";
        tableModel.setColumnIdentifiers(column);
        ArrayList<Product> products = new ArrayList<>();
        products = new Product().getAll();
        String[] data = new String[5];

        for (int i = 0; i < products.size(); i++) {
            data[0] = String.valueOf(products.get(i).getId());
            data[1] = String.valueOf(products.get(i).getCategoryId());
            data[2] = products.get(i).getName();
            data[3] = String.valueOf(products.get(i).getBrandId());
            data[4] = products.get(i).getExplanation();
            tableModel.addRow(data);

        }

        JTable table_product = new JTable(tableModel);
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
        table_product.getTableHeader().setForeground(Color.white);



        JScrollPane scrollPane = new JScrollPane(table_product, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(30, 195, 645, 390);
        this.add(scrollPane);


    }

    public void pdfGenerate(){
        String path = "";
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = jFileChooser.showSaveDialog(ProductReport.this);

        if(x == JFileChooser.APPROVE_OPTION){
            path = jFileChooser.getSelectedFile().getPath();
        }

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(path + "productReport.pdf"));
            document.open();
            PdfPTable table = new PdfPTable(5);

            //Tablo başlıklarını ekledik.
            table.addCell("Id");
            table.addCell("Category Name");
            table.addCell("Product Name");
            table.addCell("Brand Name");
            table.addCell("Explanation");

            Connection connection = Helper.getConnection();
            String query = "select Table_Product.Id, categoryName, p_name, brandName, explanation from Table_Product\n" +
                    "Inner join Table_Category on Table_Product.categoryId = Table_Category.Id\n" +
                    "Inner join Table_Brand on Table_Product.brandId = Table_Brand.Id";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                table.addCell(String.valueOf(resultSet.getInt("Id")));
                table.addCell(resultSet.getString("categoryName"));
                table.addCell(resultSet.getString("p_name"));
                table.addCell(resultSet.getString("brandName"));
                table.addCell(resultSet.getString("explanation"));

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
        backgroundImage.setIcon(Helper.createImage("src//javaProjeImage//productReport.png"));

        this.add(btnGeneratePdf);
        this.add(backgroundImage);

        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);

        this.setResizable(false);
        this.setSize(720, 650);
        this.setTitle("Product Report");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
