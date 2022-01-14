package GUI;

import Interfaces.IFrame;
import Interfaces.ITable;
import com.helper.Helper;
import model.Brand;
import model.Category;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductMenu extends JDialog implements IFrame, ITable {

    private JFormattedTextField fldProductId;
    private JFormattedTextField fldName;
    private JTextArea fldExplanation;
    private JComboBox fldCategory, fldBrand;
    private final ArrayList<JFormattedTextField> formattedTextFields = new ArrayList<>();
    private JTable table_product;
    private final ArrayList<JButton> jButtons = new ArrayList<>();
    private DefaultTableModel tableModel;
    private JPopupMenu popupMenu, popupMenu1;

    public ProductMenu() throws SQLException {
        initComponents();

    }

    @Override
    public void initComponents() throws SQLException {
        fillTable();
        JLabel backgroundImage = new JLabel();
        backgroundImage.setIcon(Helper.createImage("src/javaProjeImage/productMenu_v3.png"));

        fldProductId = new JFormattedTextField();
        fldProductId.setBounds(130, 72, 159, 28);



        fldCategory = new JComboBox();
        fldCategory.setBounds(130, 120, 130, 28);
        fldCategory.setBorder(null);
        fldCategory.setBackground(Helper.getColor(1));
        fldCategory.setFont(Helper.write(15, 2));

        ArrayList<Category> categories = new ArrayList<>();
        categories = new Category().getAll();
        for (Category category : categories) {
            fldCategory.addItem(category);
        }


        fldName = new JFormattedTextField();
        fldName.setBounds(130, 165, 159, 28);


        fldBrand = new JComboBox();
        fldBrand.setBounds(130, 210, 130, 28);
        fldBrand.setBorder(null);
        fldBrand.setBackground(Helper.getColor(1));
        fldBrand.setFont(Helper.write(15, 2));

        for (Brand brand : new Brand().getBrandNames()) {
            fldBrand.addItem(brand);
        }

        fldExplanation = new JTextArea();
        fldExplanation.setBounds(443, 72, 159, 70);
        fldExplanation.setBorder(null);
        fldExplanation.setBackground(Helper.getColor(1));
        fldExplanation.setFont(Helper.write(15, 2));


        formattedTextFields.add(fldProductId);
        formattedTextFields.add(fldName);


        for (JFormattedTextField textField : formattedTextFields) {
            textField.setSize(new Dimension(159, 28));
            textField.setFont(Helper.write(15, 2));
            textField.setBorder(null);
            textField.setBackground(Helper.getColor(1));
        }
        fldProductId.setEnabled(false);

        popupMenu = new JPopupMenu();
        JMenuItem mItem1 = new JMenuItem("Add Category");
        popupMenu.add(mItem1);
        mItem1.setFont(Helper.write(15, 1));
        JButton btnAddCategory = new JButton("+");
        btnAddCategory.setBounds(265, 120, 25, 25);
        btnAddCategory.setBackground(Helper.getColor(1));
        btnAddCategory.setBorder(null);
        btnAddCategory.setFocusable(false);
        btnAddCategory.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        mItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new CategoryMenu();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JMenuItem mItem2 = new JMenuItem("Refresh Box");
        popupMenu.add(mItem2);
        mItem2.setFont(Helper.write(15, 1));
        mItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateComboBox(1);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        popupMenu1 = new JPopupMenu();
        JMenuItem mItem3 = new JMenuItem("Add Brand");
        popupMenu1.add(mItem3);
        mItem3.setFont(Helper.write(15, 1));

        JButton btnAddBrand = new JButton("+");
        btnAddBrand.setBounds(265, 210, 25, 25);
        btnAddBrand.setBackground(Helper.getColor(1));
        btnAddBrand.setBorder(null);
        btnAddBrand.setFocusable(false);
        btnAddBrand.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                popupMenu1.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        mItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new BrandMenu();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JMenuItem mItem4 = new JMenuItem("Refresh Box");
        popupMenu1.add(mItem4);
        mItem4.setFont(Helper.write(15, 1));
        mItem4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateComboBox(2);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        JButton btnAdd = new JButton("ADD");
        btnAdd.setBounds(70, 550, 105, 55);
        btnAdd.setIcon(Helper.createImage("src\\javaProjeImage\\btnAdd.png"));
        btnAdd.setIconTextGap(-7);
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (    fldName.getText().length() == 0 ||
                        fldExplanation.getText().length() == 0
                ) {
                    JOptionPane.showMessageDialog(null, "Lütfen Alanları boş bırakmayınız !!");
                } else {
                    Product product = new Product();
                    Category casCategory = (Category) fldCategory.getSelectedItem();
                    Brand casBrand = (Brand) fldBrand.getSelectedItem();
                    product.setName(fldName.getText());
                    product.setCategoryId(casCategory.getId());
                    product.setBrandId(casBrand.getId());
                    product.setExplanation(fldExplanation.getText());
                    try {
                        new Product().Insert(product);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "Urun eklendi");
                    clearField();
                    try {
                        updateTableModel();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });


        JButton btnUpdate = new JButton("UPDATE");
        btnUpdate.setBounds(215, 550, 105, 55);
        btnUpdate.setIcon(Helper.createImage("src\\javaProjeImage\\btnUpdate.png"));
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fldProductId.getText().length() == 0 ||
                        fldName.getText().length() == 0 ||
                        fldExplanation.getText().length() == 0
                ) {
                    JOptionPane.showMessageDialog(null, "Lütfen Alanları boş bırakmayınız !!");
                } else {
                    Product product = new Product();
                    Category casCategory = (Category) fldCategory.getSelectedItem();
                    Brand casBrand = (Brand) fldBrand.getSelectedItem();
                    product.setId(Integer.parseInt(fldProductId.getText()));
                    product.setCategoryId(casCategory.getId());
                    product.setName(fldName.getText());
                    product.setBrandId(casBrand.getId());
                    product.setExplanation(fldExplanation.getText());

                    try {
                        new Product().Update(product);
                        JOptionPane.showMessageDialog(null, "Urun Güncellendi");
                        clearField();
                        updateTableModel();

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JButton btnRemove = new JButton("REMOVE");
        btnRemove.setBounds(431, 550, 105, 55);
        btnRemove.setIcon(Helper.createImage("src\\javaProjeImage\\btnRemove.png"));
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = JOptionPane.showInputDialog(null, "Silmek istediğiniz kayıtın Id'si >", "Remove Product", JOptionPane.INFORMATION_MESSAGE);
                if(id != null){
                    if(id.equalsIgnoreCase("")){
                        fldProductId.setText(String.valueOf(id));
                        if (fldProductId.getText().length() == 0) {
                            JOptionPane.showMessageDialog(null, "Lütfen Alanları boş bırakmayınız !!");
                        } else {
                            try {
                                Product product;
                                product = new Product().searchProduct(Integer.parseInt(fldProductId.getText()));
                                if(product!= null){
                                    new Product().delete(Integer.parseInt(fldProductId.getText()));
                                    JOptionPane.showMessageDialog(null, "Kayıt Silindi");
                                    clearField();
                                    updateTableModel();
                                }else{
                                    JOptionPane.showMessageDialog(null, "ÜRÜN BULUNAMADI");
                                }



                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Lütfen Id kısmını boş bırakmayınız!!!", "Uyarı", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Lütfen Id kısmını boş bırakmayınız!!!", "Uyarı", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        JButton btnCancel = new JButton("CANCEL");
        btnCancel.setBounds(576, 550, 105, 55);
        btnCancel.setIcon(Helper.createImage("src\\javaProjeImage\\btnCancel.png"));
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearField();
            }
        });

        JButton btnSearch = new JButton("SEARCH");
        btnSearch.setBounds(613, 192, 159, 70);
        btnSearch.setIcon(Helper.createImage("src\\javaProjeImage\\btnSearch.png"));
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = JOptionPane.showInputDialog(null, "Aramak istediğiniz kayıtın Id'si >", "Search Product", JOptionPane.INFORMATION_MESSAGE);
                if(id != null){
                    if(id.equalsIgnoreCase("")){
                        JOptionPane.showMessageDialog(null, "Lütfen Id kısmını boş bırakmayınız!!!", "Uyarı", JOptionPane.ERROR_MESSAGE);
                    }else{
                        fldProductId.setText(String.valueOf(Integer.parseInt(id)));
                        if (fldProductId.getText().length() != 0) {
                            try {
                                Product product;
                                product = new Product().searchProduct(Integer.parseInt(fldProductId.getText()));
                                if (product != null) {

                                    for(Category category : new Category().getAll()){
                                        if(product.getCategoryId() == category.getId()){
                                            fldCategory.getModel().setSelectedItem(category);
                                            break;
                                        }
                                    }

                                    fldName.setText(product.getName());



                                    for(Brand brand : new Brand().getAll()){
                                        if(product.getBrandId() == brand.getId()){
                                            fldBrand.getModel().setSelectedItem(brand);
                                            break;
                                        }
                                    }


                                    fldExplanation.setText(product.getExplanation());

                                } else {
                                    JOptionPane.showMessageDialog(null, "Ürün Bulunamadı!!");
                                }

                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Lütfen Alanları boş bırakmayınız !!");
                        }
                    }


                }
                else{
                    JOptionPane.showMessageDialog(null, "Lütfen Id kısmını boş bırakmayınız!!!", "Uyarı", JOptionPane.ERROR_MESSAGE);
                }


            }
        });

        JButton btnJoinTable = new JButton("JOIN TABLE");
        btnJoinTable.setIcon(Helper.createImage("src/javaProjeImage/btnJoinTable.png"));
        btnJoinTable.setSize(100, 100);
        btnJoinTable.setBounds(490, 192, 100, 100);



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
        btnJoinTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new ProductJoinTable();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });



        table_product = new JTable(tableModel);


        this.add(fldProductId);
        this.add(fldCategory);
        this.add(btnAddCategory);
        this.add(fldName);
        this.add(fldBrand);
        this.add(btnAddBrand);
        this.add(fldExplanation);
        this.add(btnAdd);
        this.add(btnUpdate);
        this.add(btnRemove);
        this.add(btnCancel);
        this.add(btnSearch);
        this.add(btnJoinTable);
        this.add(popupMenu);
        this.add(backgroundImage);

        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setTitle("Add Product");
        this.setSize(new Dimension(760, 670));
        this.setResizable(false);
        this.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void fillTable() throws SQLException {
        tableModel = new DefaultTableModel();
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
        scrollPane.setBounds(0, 280, 745, 200);
        this.add(scrollPane);


    }

    public void updateTableModel() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_product.getModel();
        clearModel.setRowCount(0);
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


    }

    public void clearField() {
        fldProductId.setText(null);
        fldCategory.setSelectedItem(null);
        fldName.setText(null);
        fldBrand.setSelectedItem(null);
        fldExplanation.setText(null);

    }

    private void updateComboBox(int n) throws SQLException {
        if(n == 1){
            fldCategory.removeAllItems();
            ArrayList<Category> categories = new ArrayList<>();
            categories = new Category().getAll();
            for (Category category : categories) {
                fldCategory.addItem(category);
            }
        }else if(n == 2){
            fldBrand.removeAllItems();
            for (Brand brand : new Brand().getBrandNames()) {
                fldBrand.addItem(brand);
            }
        }

    }

}




