package GUI;

import Interfaces.IFrame;
import Interfaces.ITable;
import com.helper.Helper;
import model.Brand;
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

public class BrandMenu extends JDialog implements IFrame, ITable {
    private JFormattedTextField fldBrandId, fldBrandName;
    private JButton btn;
    private JTable table_brand;
    private DefaultTableModel brandModel;
    private ArrayList<JFormattedTextField> jFormattedTextFields = new ArrayList<>();
    private JPopupMenu popupMenu;
    private JMenuItem itemInsert, itemUpdate, itemRemove;

    public BrandMenu() throws SQLException {
        initComponents();
    }

    @Override
    public void initComponents() throws SQLException {

        fillTable();

        fldBrandId = new JFormattedTextField();
        fldBrandId.setBounds(217, 154, 159, 28);

        fldBrandName = new JFormattedTextField();
        fldBrandName.setBounds(217, 205, 159, 28);

        jFormattedTextFields.add(fldBrandId);
        jFormattedTextFields.add(fldBrandName);

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
        btn.setBounds(206, 537, 80, 44);
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
                if(fldBrandName.getText().length() == 0){
                    JOptionPane.showMessageDialog(null, "GEREKLİ ALANLARI DOLDURUNUZ");
                }else{
                    Brand brand = new Brand();
                    brand.setBrandName(fldBrandName.getText());
                    try {
                        new Brand().Insert(brand);
                        JOptionPane.showMessageDialog(null, "MARKA BAŞARIYLA EKLENDİ");
                        updateTableModel();
                        clearField();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        itemUpdate = new JMenuItem("UPDATE");
        itemUpdate.setFont(Helper.write(20, 1));
        popupMenu.add(itemUpdate);

        itemUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fldBrandId.getText().length() == 0 || fldBrandName.getText().length() == 0){
                    JOptionPane.showMessageDialog(null, "GEREKLİ ALANLARI DOLDURUNUZ");
                }else{
                    Brand brand = new Brand();
                    brand.setId(Integer.parseInt(fldBrandId.getText()));
                    brand.setBrandName(fldBrandName.getText());

                    try {
                        new Brand().Update(brand);
                        JOptionPane.showMessageDialog(null, "MARKA BAŞARIYLA GUNCELLENDİ");
                        updateTableModel();
                        clearField();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        itemRemove = new JMenuItem("REMOVE");
        itemRemove.setFont(Helper.write(20, 1));
        popupMenu.add(itemRemove);

        itemRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fldBrandId.getText().length() == 0){
                    JOptionPane.showMessageDialog(null, "ID DEGERİ GİRİNİZ");
                }else{
                    try {
                        new Brand().delete(Integer.parseInt(fldBrandId.getText()));
                        JOptionPane.showMessageDialog(null, "Marka SİLİNDİ");
                        updateTableModel();
                        clearField();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });



        JLabel backgroundImage = new JLabel();
        backgroundImage.setIcon(Helper.createImage("src/javaProjeImage/brandMenu.png"));

        this.add(fldBrandName);
        this.add(fldBrandId);
        this.add(btn);
        this.add(backgroundImage);

        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setSize(500, 650);
        this.setResizable(false);
        this.setTitle("Brand Menu");
        this.setVisible(true);

    }

    @Override
    public void fillTable() throws SQLException {
        brandModel = new DefaultTableModel();
        Object[] column = {"Id", "Brand Name"};
        brandModel.setColumnIdentifiers(column);
        ArrayList<Brand> brands = new ArrayList<>();
        brands = new Brand().getAll();
        String [] data = new String[2];

        for(int i = 0; i < brands.size(); i++){
            data[0] = String.valueOf(brands.get(i).getId());
            data[1] = String.valueOf(brands.get(i).getBrandName());
            brandModel.addRow(data);
        }

        table_brand = new JTable(brandModel);
        table_brand.setRowHeight(25);
        table_brand.setDefaultEditor(Object.class, null);
        table_brand.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table_brand.setSelectionBackground(Helper.getColor(1));
        table_brand.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table_brand.getTableHeader().setOpaque(false);
        table_brand.getTableHeader().setBackground(Helper.getColor(6));
        table_brand.setShowVerticalLines(false);
        table_brand.getTableHeader().setBorder(null);
        table_brand.setFont(new Font("Segoe UI", Font.BOLD, 15));
        table_brand.setFocusable(false);
        table_brand.setShowGrid(false);
        table_brand.setIntercellSpacing(new Dimension(0,0));

        JScrollPane scrollPane = new JScrollPane(table_brand, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(55, 270, 385, 208);
        this.add(scrollPane);
        
    }

    @Override
    public void updateTableModel() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_brand.getModel();
        clearModel.setRowCount(0);
        ArrayList<Brand> brands = new ArrayList<>();
        brands = new Brand().getAll();
        String [] data = new String[2];

        for(int i = 0; i < brands.size(); i++){
            data[0] = String.valueOf(brands.get(i).getId());
            data[1] = String.valueOf(brands.get(i).getBrandName());
            brandModel.addRow(data);
        }

    }

    public void clearField(){
        fldBrandId.setText(null);
        fldBrandName.setText(null);

    }
}
