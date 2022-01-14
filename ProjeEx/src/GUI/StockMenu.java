package GUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import Interfaces.IFrame;
import com.helper.Helper;
import model.Product;
import model.Stock;

public class StockMenu extends JDialog implements IFrame {

    //PRODUCT NAME KISMINI COMBOBOX YAP !!!!
    private JFormattedTextField fldStockId;
    private JComboBox fldProductName;
    private JFormattedTextField fldProductPrice;
    private JFormattedTextField fldProductAmount;
    private JFormattedTextField fldTotalStock;
    private ArrayList<JFormattedTextField> jFormattedTextFields = new ArrayList<>();
    private ArrayList<JButton> jButtons = new ArrayList<>();
    private JTable table_stock;
    private DefaultTableModel tableModel;

    public StockMenu() throws HeadlessException, SQLException {
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
        scrollPane.setBounds(0, 280, 745, 200);
        this.add(scrollPane);

    }

    public void updateTableModel() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_stock.getModel();
        clearModel.setRowCount(0);
        ArrayList<Stock> stocks = new ArrayList<>();
        stocks = new Stock().getAll();
        String[] data = new String[4];

        for(int i = 0; i < stocks.size(); i++){
            data[0] = String.valueOf(stocks.get(i).getId());
            data[1] = String.valueOf(stocks.get(i).getProductId());
            data[2] = String.valueOf(stocks.get(i).getPrice());
            data[3] = String.valueOf(stocks.get(i).getAmount());
            tableModel.addRow(data);
        }
    }

    public void clearField() throws SQLException {
        fldStockId.setText(null);
        fldProductName.setSelectedItem(null);
        fldProductPrice.setText(null);
        fldProductAmount.setText(null);
        fldTotalStock.setText(String.valueOf(new Stock().totalStock()));

    }

    @Override
    public void initComponents() throws SQLException {
        fillTable();
        JLabel backgroundImage = new JLabel();
        backgroundImage.setIcon(Helper.createImage("src/javaProjeImage/stockMenu_v2.png"));

        fldStockId = new JFormattedTextField();
        fldStockId.setBounds(130, 77, 159, 28);
        fldStockId.setEnabled(false);

        fldProductName = new JComboBox();
        fldProductName.setBounds(130, 125, 159, 28);
        fldProductName.setBorder(null);
        fldProductName.setBackground(Helper.getColor(1));
        fldProductName.setFont(Helper.write(15, 2));

        for(Product product : new Product().getAll()){
            fldProductName.addItem(product);
        }

        fldProductPrice = new JFormattedTextField();
        fldProductPrice.setBounds(130, 170, 159, 28);
        fldProductPrice.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Helper.formatText(e, "number");
            }
        });

        fldProductAmount = new JFormattedTextField();
        fldProductAmount.setBounds(130, 215, 159, 28);
        fldProductAmount.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Helper.formatText(e, "number");
            }
        });

        fldTotalStock = new JFormattedTextField();
        fldTotalStock.setBounds(555, 77, 159, 28);
        fldTotalStock.setText(String.valueOf(new Stock().totalStock()));
        fldTotalStock.setEditable(false);


        jFormattedTextFields.add(fldStockId);
        jFormattedTextFields.add(fldProductPrice);
        jFormattedTextFields.add(fldProductAmount);
        jFormattedTextFields.add(fldTotalStock);

        for (JFormattedTextField textField : jFormattedTextFields) {
            textField.setSize(new Dimension(159, 28));
            textField.setFont(Helper.write(15, 2));
            textField.setBorder(null);
            textField.setBackground(Helper.getColor(1));
        }


        JButton btnAdd = new JButton("ADD");
        btnAdd.setBounds(70, 555, 105, 55);
        btnAdd.setIcon(Helper.createImage("C:\\JavaClass\\ProjeEx\\src\\javaProjeImage\\btnAdd.png"));
        btnAdd.setIconTextGap(-7);
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fldProductAmount.getText().length() == 0 || fldProductPrice.getText().length() == 0){
                    JOptionPane.showMessageDialog(null, "Lütfen Alanları boş bırakmayınız !!");
                }else{
                    Product casProduct = (Product) fldProductName.getSelectedItem();

                    try {
                        if(new Stock().searchStockByProduct(casProduct.getId())){
                            JOptionPane.showMessageDialog(null, "Ürün zaten stoklarda mevcut tekrar ekleyemezsiniz!");
                        }else{
                            Stock stock = new Stock();
                            stock.setProductId(casProduct.getId());
                            stock.setAmount(Integer.parseInt(fldProductAmount.getText()));
                            stock.setPrice(Double.parseDouble(fldProductPrice.getText()));
                            try {
                                new Stock().Insert(stock);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                            JOptionPane.showMessageDialog(null, "Stok ekleme başarılı");
                            try {
                                clearField();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                            try {
                                updateTableModel();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }




                }
            }
        });


        JButton btnUpdate = new JButton("UPDATE");
        btnUpdate.setBounds(215, 555, 105, 55);
        btnUpdate.setIcon(Helper.createImage("C:\\JavaClass\\ProjeEx\\src\\javaProjeImage\\btnUpdate.png"));
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fldProductAmount.getText().length() == 0 || fldProductPrice.getText().length() == 0 || fldStockId.getText().length() == 0){
                    JOptionPane.showMessageDialog(null, "Lütfen Alanları boş bırakmayınız !!");
                }else{
                    Stock stock = new Stock();
                    Product casProduct = (Product) fldProductName.getSelectedItem();
                    stock.setId(Integer.parseInt(fldStockId.getText()));
                    stock.setProductId(casProduct.getId());
                    stock.setAmount(Integer.parseInt(fldProductAmount.getText()));
                    stock.setPrice(Double.parseDouble(fldProductPrice.getText()));
                    try {
                        new Stock().Update(stock);
                        JOptionPane.showMessageDialog(null, "Stock Güncellendi");
                        updateTableModel();
                        clearField();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JButton btnRemove = new JButton("REMOVE");
        btnRemove.setBounds(431, 555, 105, 55);
        btnRemove.setIcon(Helper.createImage("C:\\JavaClass\\ProjeEx\\src\\javaProjeImage\\btnRemove.png"));
        btnRemove.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String id = JOptionPane.showInputDialog(null, "Silmek istediğiniz kayıtın Id'si >", "Remove Stock", JOptionPane.INFORMATION_MESSAGE);
                if(id != null){
                    if(id.equalsIgnoreCase("")){
                        JOptionPane.showMessageDialog(null, "Lütfen Id kısmını boş bırakmayınız!!!", "Uyarı", JOptionPane.ERROR_MESSAGE);
                    }else{
                        fldStockId.setText(String.valueOf(id));
                        if(fldStockId.getText().length() == 0){
                            JOptionPane.showMessageDialog(null, "Lütfen Silinecek kayıt için Id degeri giriniz");
                        }else{
                            try {
                                Stock stock;
                                stock = new Stock().searchStock(Integer.parseInt(fldStockId.getText()));
                                if(stock != null){
                                    new Stock().delete(Integer.parseInt(fldStockId.getText()));
                                    JOptionPane.showMessageDialog(null, "Kayıt Silindi");
                                    clearField();
                                    updateTableModel();
                                }else{
                                    JOptionPane.showMessageDialog(null, "STOK BULUNAMADI");
                                }


                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }

                        }
                    }

                }else{
                    JOptionPane.showMessageDialog(null, "Lütfen Id kısmını boş bırakmayınız!!!", "Uyarı", JOptionPane.ERROR_MESSAGE);

                }

            }
        });

        JButton btnCancel = new JButton("CANCEL");
        btnCancel.setBounds(576, 555, 105, 55);
        btnCancel.setIcon(Helper.createImage("C:\\JavaClass\\ProjeEx\\src\\javaProjeImage\\btnCancel.png"));
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clearField();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton btnSearch = new JButton("SEARCH");
        btnSearch.setBounds(613, 195, 159, 70);
        btnSearch.setIcon(Helper.createImage("C:\\JavaClass\\ProjeEx\\src\\javaProjeImage\\btnSearch.png"));
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = JOptionPane.showInputDialog(null, "Aramak istediğiniz kayıtın Id'si >", "Search Stock", JOptionPane.INFORMATION_MESSAGE);
                if(id != null){
                    if(id.equalsIgnoreCase("")){
                        JOptionPane.showMessageDialog(null, "Lütfen Id kısmını boş bırakmayınız!!!", "Uyarı", JOptionPane.ERROR_MESSAGE);
                    }else{
                        fldStockId.setText(String.valueOf(Integer.parseInt(id)));
                        if(fldStockId.getText().length() == 0){
                            JOptionPane.showMessageDialog(null, "Lütfen Alanları boş bırakmayınız !!");
                        }else{
                            try {
                                Stock stock;
                                stock = new Stock().searchStock(Integer.parseInt(fldStockId.getText()));
                                if(stock != null){


                                    for(Product product : new Product().getAll()){
                                        if(product.getId() == stock.getProductId()){
                                            fldProductName.getModel().setSelectedItem(product);
                                            break;
                                        }
                                    }

                                    fldProductPrice.setText(String.valueOf(stock.getPrice()));
                                    fldProductAmount.setText(String.valueOf(stock.getAmount()));
                                }else{
                                    JOptionPane.showMessageDialog(null, "Stok Bulunamadı!!");
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                }else{
                    JOptionPane.showMessageDialog(null, "Lütfen Id kısmını boş bırakmayınız!!!", "Uyarı", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        JButton btnJoinTable = new JButton("JOIN TABLE");
        btnJoinTable.setIcon(Helper.createImage("src/javaProjeImage/btnJoinTable.png"));
        btnJoinTable.setSize(100, 100);
        btnJoinTable.setBounds(488, 195, 100, 100);
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

        jButtons.add(btnAdd);
        jButtons.add(btnUpdate);
        jButtons.add(btnRemove);
        jButtons.add(btnCancel);
        jButtons.add(btnSearch);
        jButtons.add(btnJoinTable);

        for (JButton button : jButtons) {
            button.setBorder(null);
            button.setSize(new Dimension(105, 55));
            button.setForeground(Helper.getColor(4));
            button.setBackground(Helper.getColor(3));
            button.setFocusable(false);
            button.setVerticalTextPosition(JButton.TOP);
            button.setHorizontalTextPosition(JButton.CENTER);
            button.setFont(Helper.write(20, 1));
        }

        btnSearch.setBackground(Helper.getColor(1));
        btnSearch.setForeground(Helper.getColor(2));
        btnSearch.setFont(Helper.write(15, 1));

        btnJoinTable.setBackground(Helper.getColor(1));
        btnJoinTable.setForeground(Helper.getColor(2));
        btnJoinTable.setFont(Helper.write(15, 1));


        this.add(fldStockId);
        this.add(fldProductName);
        this.add(fldProductPrice);
        this.add(fldProductAmount);
        this.add(fldTotalStock);
        this.add(btnAdd);
        this.add(btnUpdate);
        this.add(btnRemove);
        this.add(btnCancel);
        this.add(btnSearch);
        this.add(btnJoinTable);
        this.add(backgroundImage);

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setSize(new Dimension(760, 680));
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setResizable(false);
        this.setTitle("Stock Menu");
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
}
