package GUI;

import Interfaces.IFrame;
import com.helper.Helper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerReport extends JDialog implements IFrame {
    private JTable table_customer;
    private JButton btnGeneratePdf;
    private DefaultTableModel tableModel;

    public CustomerReport() throws HeadlessException, SQLException {

        initComponents();
    }

    public void pdfGenerate(){
        String path = "";
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = jFileChooser.showSaveDialog(CustomerReport.this);

        if(x == JFileChooser.APPROVE_OPTION){
            path = jFileChooser.getSelectedFile().getPath();
        }

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(path + "abc123.pdf"));
            document.open();
            PdfPTable table = new PdfPTable(5);

            //Tablo başlıklarını ekledik.
            table.addCell("Id");
            table.addCell("Customer Name");
            table.addCell("Customer Surname");
            table.addCell("Customer Phone Number");
            table.addCell("Customer Account Number");

            ArrayList<Customer> customers = new ArrayList<>();
            customers = new Customer().getAll();

            for(int i = 0; i < customers.size(); i++){
                table.addCell(String.valueOf(customers.get(i).getId()));
                table.addCell(String.valueOf(customers.get(i).getName()));
                table.addCell(String.valueOf(customers.get(i).getSurname()));
                table.addCell(String.valueOf(customers.get(i).getPhoneNumber()));
                table.addCell(String.valueOf(customers.get(i).getAccountNumber()));
            }
            document.add(table);

        } catch (DocumentException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        document.close();

    }

    public void fillTable() throws SQLException {
        tableModel = new DefaultTableModel();
        Object[] column = new Object[6];
        column[0] = "Id";
        column[1] = "Name";
        column[2] = "Surname";
        column[3] = "Phone Number";
        column[4] = "Email";
        column[5] = "Account Number";
        tableModel.setColumnIdentifiers(column);
        ArrayList<Customer> customers = new ArrayList<>();
        customers = new Customer().getAll();
        String[] data = new String[6];

        for(int i = 0; i < customers.size(); i++){
            data[0] = String.valueOf(customers.get(i).getId());
            data[1] = customers.get(i).getName();
            data[2] = customers.get(i).getSurname();
            data[3] = customers.get(i).getPhoneNumber();
            data[4] = customers.get(i).getEmail();
            data[5] = customers.get(i).getAccountNumber();
            tableModel.addRow(data);

        }

        table_customer = new JTable(tableModel);
        table_customer.setRowHeight(25);
        table_customer.setDefaultEditor(Object.class, null);
        table_customer.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table_customer.setSelectionBackground(Helper.getColor(1));
        table_customer.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table_customer.getTableHeader().setOpaque(false);
        table_customer.getTableHeader().setBackground(Helper.getColor(6));
        table_customer.setShowVerticalLines(false);
        table_customer.getTableHeader().setBorder(null);
        table_customer.setFont(new Font("Segoe UI", Font.BOLD, 15));
        table_customer.setFocusable(false);
        table_customer.getTableHeader().setForeground(Color.white);


        JScrollPane scrollPane = new JScrollPane(table_customer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(30, 195, 645, 390);
        this.add(scrollPane);



    }

    @Override
    public void initComponents() throws SQLException {
        fillTable();

        btnGeneratePdf = new JButton("Generate PDF");
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
        backgroundImage.setIcon(Helper.createImage("src//javaProjeImage//customerReport.png"));


        this.add(btnGeneratePdf);
        this.add(backgroundImage);

        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
        this.setSize(720, 650);
        this.setTitle("Customer Report");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
}
