package GUI;

import Interfaces.IFrame;
import Interfaces.ITable;
import com.helper.Helper;
import model.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryMenu extends JDialog implements IFrame , ITable {
    private JFormattedTextField fldCategoryId, fldCategoryName;
    private JButton btn;
    private JTable table_category;
    private DefaultTableModel categoryModel;
    private ArrayList<JFormattedTextField> jFormattedTextFields = new ArrayList<>();
    private JPopupMenu popupMenu;
    private JMenuItem itemInsert, itemUpdate, itemRemove;

    public CategoryMenu() throws SQLException {
        initComponents();
    }

    @Override
    public void initComponents() throws SQLException {

        fillTable();

        fldCategoryId = new JFormattedTextField();
        fldCategoryId.setBounds(219, 154, 159, 28);

        fldCategoryName = new JFormattedTextField();
        fldCategoryName.setBounds(219, 202, 159, 28);

        jFormattedTextFields.add(fldCategoryId);
        jFormattedTextFields.add(fldCategoryName);

        for (JFormattedTextField textField : jFormattedTextFields) {
            textField.setSize(new Dimension(159, 28));
            textField.setFont(Helper.write(15, 2));
            textField.setBorder(null);
            textField.setBackground(Helper.getColor(1));
        }

        popupMenu = new JPopupMenu();
        itemInsert = new JMenuItem("Add");
        itemInsert.setFont(Helper.write(20, 1));
        popupMenu.add(itemInsert);


        btn = new JButton();
        btn.setBounds(210, 540, 80, 44);
        btn.setFont(Helper.write(15, 1));
        btn.setFocusable(false);
        btn.setBackground(Color.white);
        btn.setText("CLICK");
        btn.setBorder(null);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        itemInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fldCategoryName.getText().length() == 0){
                    JOptionPane.showMessageDialog(null, "GEREKLİ ALANLARI DOLDURUNUZ");
                }else{
                    Category category = new Category();
                    category.setName(fldCategoryName.getText());
                    try {
                        new Category().Insert(category);
                        JOptionPane.showMessageDialog(null, "KATEGORİ BAŞARIYLA EKLENDİ");
                        updateTableModel();
                        clearField();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        itemUpdate = new JMenuItem("Update");
        itemUpdate.setFont(Helper.write(20, 1));
        popupMenu.add(itemUpdate);
        itemUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(fldCategoryName.getText().length() == 0 || fldCategoryId.getText().length() == 0){
                    JOptionPane.showMessageDialog(null, "GEREKLİ ALANLARI DOLDURUNUZ");

                }else{
                    Category category = new Category();
                    category.setId(Integer.parseInt(fldCategoryId.getText()));
                    category.setName(fldCategoryName.getText());
                    try {
                        new Category().Update(category);
                        JOptionPane.showMessageDialog(null, "KATEGORİ BAŞARIYLA GUNCELLENDİ");
                        updateTableModel();
                        clearField();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }


            }
        });

        itemRemove = new JMenuItem("Remove");
        itemRemove.setFont(Helper.write(20, 1));
        popupMenu.add(itemRemove);
        itemRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(fldCategoryId.getText().length() == 0){
                    JOptionPane.showMessageDialog(null, "GEREKLİ ALANLARI DOLDURUNUZ");
                }else{
                    try {

                        new Category().delete(Integer.parseInt(fldCategoryId.getText()));
                        JOptionPane.showMessageDialog(null, "KATEGORİ SİLİNDİ");

                        updateTableModel();
                        clearField();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }


            }
        });


        JLabel backgroundImage = new JLabel();
        backgroundImage.setIcon(Helper.createImage("src/javaProjeImage/categoryMenu_v2.png"));

        this.add(fldCategoryId);
        this.add(fldCategoryName);
        this.add(btn);
        this.add(backgroundImage);

        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setSize(500, 650);
        this.setResizable(false);
        this.setTitle("Category Menu");
        this.setVisible(true);

    }

    @Override
    public void fillTable() throws SQLException {
        categoryModel = new DefaultTableModel();
        Object[] column = {"Id", "Name"};
        categoryModel.setColumnIdentifiers(column);
        ArrayList<Category> categories = new ArrayList<>();
        categories = new Category().getAll();
        String [] data = new String[2];

        for(int i = 0; i < categories.size(); i++){
            data[0] = String.valueOf(categories.get(i).getId());
            data[1] = String.valueOf(categories.get(i).getName());
            categoryModel.addRow(data);
        }

        table_category = new JTable(categoryModel);
        table_category.setRowHeight(25);
        table_category.setDefaultEditor(Object.class, null);
        table_category.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table_category.setSelectionBackground(Helper.getColor(1));
        table_category.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table_category.getTableHeader().setOpaque(false);
        table_category.getTableHeader().setBackground(Helper.getColor(6));
        table_category.setShowVerticalLines(false);
        table_category.getTableHeader().setBorder(null);
        table_category.setFont(new Font("Segoe UI", Font.BOLD, 15));
        table_category.setFocusable(false);
        table_category.setShowGrid(false);
        table_category.setIntercellSpacing(new Dimension(0,0));

        JScrollPane scrollPane = new JScrollPane(table_category, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(55, 270, 385, 208);
        this.add(scrollPane);

    }

    @Override
    public void updateTableModel() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_category.getModel();
        clearModel.setRowCount(0);
        String [] data = new String[2];
        ArrayList<Category> categories = new ArrayList<>();
        categories = new Category().getAll();
        for(int i = 0; i < categories.size(); i++){
            data[0] = String.valueOf(categories.get(i).getId());
            data[1] = String.valueOf(categories.get(i).getName());
            categoryModel.addRow(data);
        }


    }

    public void clearField(){
        fldCategoryName.setText(null);
        fldCategoryId.setText(null);
    }


}
