package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import Interfaces.IFrame;
import com.helper.Helper;
import model.Customer;
import model.Product;
import model.Stock;

public class SellProduct extends JDialog implements IFrame {
    private JFormattedTextField fldStockId,fldProductPrice, fldProductAmount;
    private JComboBox fldCustomerName, fldProductName;
    private ArrayList<JFormattedTextField> jFormattedTextFields = new ArrayList<>();
    private JButton btnAdd, btnSell, btnCancel, btnJoinTable;
    private ArrayList<JButton> jButtons = new ArrayList<>();
    private JTable table_stock, table_sell;
    private DefaultTableModel tableModel, sellModel;
    private ArrayList<Stock> changeStock = new ArrayList<>();
    private ArrayList<model.SellProduct> sellProducts = new ArrayList<>();



    public SellProduct() throws HeadlessException, SQLException {
        initComponents();

    }

    public void fillTable() throws SQLException {
        tableModel = new DefaultTableModel();
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
        scrollPane.setBounds(0, 280, 375, 225);
        this.add(scrollPane);
    }

    @Override
    public void initComponents() throws SQLException {
        fillTable();

        fldStockId = new JFormattedTextField();
        fldStockId.setBounds(130, 77, 159, 28);
        fldStockId.setEnabled(false);

        fldProductName = new JComboBox();
        fldProductName.setBounds(130, 125, 159, 28);
        fldProductName.setBorder(null);
        fldProductName.setFocusable(false);
        fldProductName.setBackground(Helper.getColor(1));
        fldProductName.setFont(Helper.write(15, 2));


        for(Product product : new Product().getAll()){
            fldProductName.addItem(product);
        }

        fldProductName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    fillBySearchProductId();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        fldProductPrice = new JFormattedTextField();
        fldProductPrice.setBounds(130, 170, 159, 28);
        fldProductPrice.setEditable(false);
        //fldProductPrice.setForeground(Helper.getColor(2));

        fldProductAmount = new JFormattedTextField();
        fldProductAmount.setBounds(130, 213, 159, 28);
        fldProductAmount.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Helper.formatText(e, "number");
            }
        });

        fldCustomerName = new JComboBox();
        fldCustomerName.setBounds(557, 77, 155, 26);
        fldCustomerName.setFont(Helper.write(15, 2));
        fldCustomerName.setBorder(null);
        fldCustomerName.setFocusable(false);
        fldCustomerName.setBackground(Helper.getColor(1));


        for(Customer customer : new Customer().getAll()){
            fldCustomerName.addItem(customer);
        }

        jFormattedTextFields.add(fldStockId);
        jFormattedTextFields.add(fldProductPrice);
        jFormattedTextFields.add(fldProductAmount);

        for (JFormattedTextField textField : jFormattedTextFields) {
            textField.setSize(new Dimension(159, 28));
            textField.setFont(Helper.write(15, 2));
            textField.setBorder(null);
            textField.setBackground(Helper.getColor(1));
        }

        btnAdd = new JButton("ADD");
        btnAdd.setBounds(350, 215, 53, 27);
        btnAdd.setBorder(null);
        btnAdd.setForeground(Helper.getColor(2));
        btnAdd.setBackground(Helper.getColor(1));
        btnAdd.setFocusable(false);
        btnAdd.setVerticalTextPosition(JButton.TOP);
        btnAdd.setHorizontalTextPosition(JButton.CENTER);
        btnAdd.setFont(Helper.write(15, 1));
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if(fldProductAmount.getText().length() == 0){
                        JOptionPane.showMessageDialog(null, "LUTFEN MIKTAR GİRİNİZ");
                    }else{
                        if(check()){

                            Stock stock = new Stock();
                            Product casProduct = (Product) fldProductName.getSelectedItem();
                            if(productIsEmpty(casProduct)){
                                JOptionPane.showMessageDialog(null, "LUTFEN BIR URUN SECINIZ");
                            }else{
                                stock.setId(Integer.parseInt(fldStockId.getText()));
                                stock.setProductId(casProduct.getId());
                                stock.setPrice(Double.parseDouble(fldProductPrice.getText()));
                                stock.setAmount(Integer.parseInt(fldProductAmount.getText()));

                                model.SellProduct sellProduct = new model.SellProduct();
                                Customer casCustomer = (Customer) fldCustomerName.getSelectedItem();
                                sellProduct.setProductId(casProduct.getId());
                                sellProduct.setPrice(Double.parseDouble(fldProductPrice.getText()));
                                sellProduct.setAmount(Integer.parseInt(fldProductAmount.getText()));
                                sellProduct.setStockId(Integer.parseInt(fldStockId.getText()));
                                sellProduct.setCustomerId(casCustomer.getId());
                                sellProducts.add(sellProduct);


                                changeStock.add(stock);
                                fillSellText(stock);
                                clearField();
                            }

                        }else{
                            JOptionPane.showMessageDialog(null, "STOKTA URUN MEVCUT DEGIL SATIS GERCEKTESTIRILEMEZ");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }




            }
        });



        btnSell = new JButton("SELL PRODUCT");
        btnSell.setBounds(270, 555, 105, 55);
        btnSell.setIcon(Helper.createImage("src\\javaProjeImage\\btnSell.png"));
        btnSell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    stockSell();
                    updateSellTable();
                    fillBySearchProductId();
                    JOptionPane.showMessageDialog(null, "Satış Gerçekleştirildi");
                    for(model.SellProduct sellProduct : sellProducts){
                        sellProduct.Insert(sellProduct);
                        clearField();
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnCancel = new JButton("CANCEL");
        btnCancel.setBounds(400,555,105,55);
        btnCancel.setIcon(Helper.createImage("C:\\JavaClass\\ProjeEx\\src\\javaProjeImage\\btnCancel.png"));
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearField();
                fldProductName.setSelectedItem(null);
            }
        });


        btnJoinTable = new JButton("JOIN TABLE");
        btnJoinTable.setIcon(Helper.createImage("src/javaProjeImage/btnJoinTable.png"));
        btnJoinTable.setSize(100, 100);
        btnJoinTable.setBounds(613, 195, 159, 70);
        btnJoinTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new StockJoinTable();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });



        jButtons.add(btnSell);
        jButtons.add(btnCancel);
        jButtons.add(btnJoinTable);

        for (JButton button : jButtons) {
            button.setSize(new Dimension(105, 55));
            button.setForeground(Helper.getColor(4));
            button.setBackground(Helper.getColor(3));
            button.setFocusable(false);
            button.setVerticalTextPosition(JButton.TOP);
            button.setHorizontalTextPosition(JButton.CENTER);
            button.setFont(Helper.write(15, 1));
            button.setBorder(null);
        }

        btnJoinTable.setBackground(Helper.getColor(1));
        btnJoinTable.setForeground(Helper.getColor(2));
        btnJoinTable.setFont(Helper.write(15, 1));



        sellModel = new DefaultTableModel();
        Object[] column = {"Id", "Product Id", "Price", "Amount", "Customer Id"};
        sellModel.setColumnIdentifiers(column);
        table_sell = new JTable(sellModel);
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
        JScrollPane scrollPane = new JScrollPane(table_sell, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(380, 280, 375, 225);
        this.add(scrollPane);





        JLabel backgroundImage = new JLabel();
        backgroundImage.setIcon(Helper.createImage("src/javaProjeImage/sellProductMenu_v5.png"));

        this.add(fldStockId);
        this.add(fldProductName);
        this.add(fldProductPrice);
        this.add(fldProductAmount);
        this.add(fldCustomerName);
        this.add(btnAdd);
        this.add(btnSell);
        this.add(btnCancel);
        this.add(btnJoinTable);

        this.add(backgroundImage);


        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setSize(new Dimension(760,680));
        this.setResizable(false);
        this.setTitle("Sell Product");
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public void fillBySearchProductId() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_stock.getModel();
        clearModel.setRowCount(0);
        ArrayList<Stock> stocks = new ArrayList<>();
        Product casProduct = (Product) fldProductName.getSelectedItem();
        if(productIsEmpty(casProduct)){
            JOptionPane.showMessageDialog(null, "LUTFEN BIR URUN SECINIZ");
        }else{
            System.out.println("casproduct.getId ===" + casProduct.getId());
            stocks = new Stock().searchStockByProductId(casProduct.getId());
        }

        


        for(Stock stock : stocks){
            fldProductPrice.setText(String.valueOf(stock.getPrice()));
            fldStockId.setText(String.valueOf(stock.getId()));
            break;
        }

        String data[] = new String[4];

        for(int i = 0; i < stocks.size(); i++){
            data[0] = String.valueOf(stocks.get(i).getId());
            data[1] = String.valueOf(stocks.get(i).getProductId());
            data[2] = String.valueOf(stocks.get(i).getPrice());
            data[3] = String.valueOf(stocks.get(i).getAmount());
            tableModel.addRow(data);
        }

    }

    public void fillSellText(Stock stock){
        String data[] = new String[5];
        Customer casCustomer = (Customer) fldCustomerName.getSelectedItem();
        data[0] = String.valueOf(stock.getId());
        data[1] = String.valueOf(stock.getProductId());
        data[2] = String.valueOf(stock.getPrice());
        data[3] = String.valueOf(stock.getAmount());
        data[4] = String.valueOf(casCustomer.getId());
        sellModel.addRow(data);
    }

    public boolean check() throws SQLException {
        ArrayList<Stock> stocks = new ArrayList<>();
        Product casProduct = (Product) fldProductName.getSelectedItem();
        System.out.println("casproduct.getId ===" + casProduct.getId());
        stocks = new Stock().searchStockByProductId(casProduct.getId());


        int amount = 0;
        for(Stock stock : stocks){
            fldProductPrice.setText(String.valueOf(stock.getPrice()));
            fldStockId.setText(String.valueOf(stock.getId()));
            amount = stock.getAmount();
            break;
        }


        if(stocks.size() == 0){
            return false;
        }else if(Integer.parseInt(fldProductAmount.getText()) > amount){
            return false;
        }
        return true;
    }

    public void clearField(){
        fldStockId.setText(null);
        fldProductPrice.setText(null);
        fldProductAmount.setText(null);
        //fldProductName.setSelectedItem(null);
    }

    public void stockSell() throws SQLException {
        for(Stock stock : changeStock){
            stock.decreaseStock(stock);
        }
    }

    public void updateSellTable(){
        DefaultTableModel clearModel = (DefaultTableModel) table_sell.getModel();
        clearModel.setRowCount(0);
    }

    public boolean productIsEmpty(Product product){
        if(product == null){
            return true;
        }
        return false;
    }
}
