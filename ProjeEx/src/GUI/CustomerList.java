package GUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import Interfaces.IFrame;
import Interfaces.ITable;
import com.helper.Helper;
import model.Customer;

public class CustomerList extends JDialog implements IFrame, ITable {

    private final Color color = new Color(192,192,192);
    private Color color1 = new Color(74,128,77);
    private final Color color2 = new Color(58,105,61);
    private final Color color3 = new Color(219,228,235);
    private JFormattedTextField fldId;
    private JFormattedTextField fldCustomerId;
    private JFormattedTextField fldCustomerName;
    private JFormattedTextField fldCustomerSurname;
    private JFormattedTextField fldCustomerPhoneNumber;
    private JFormattedTextField fldCustomerAccountNumber;
    private JFormattedTextField fldCustomerEmail;
    private ArrayList<JFormattedTextField> formattedTextFields = new ArrayList<>();
    private JTable table_customer;
    private JButton btnSearch, btnUpdate, btnRemove, btnCancel;
    private ArrayList<JButton> jButtons = new ArrayList<>();
    DefaultTableModel tableModel;

    public CustomerList() {
        initComponents();
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




        JScrollPane scrollPane = new JScrollPane(table_customer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0,280,745,200);
        this.add(scrollPane);



    }

    public void updateTableModel() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_customer.getModel();
        clearModel.setRowCount(0);
        ArrayList<Customer> customers = new ArrayList<>();
        customers = new Customer().getAll();
        String[] data = new String[6];


        for(int i = 0; i < customers.size(); i++) {
            data[0] = String.valueOf(customers.get(i).getId());
            data[1] = customers.get(i).getName();
            data[2] = customers.get(i).getSurname();
            data[3] = customers.get(i).getPhoneNumber();
            data[4] = customers.get(i).getEmail();
            data[5] = customers.get(i).getAccountNumber();
            tableModel.addRow(data);
        }


        }

        public void clearField(){
            fldCustomerId.setText(null);
            fldCustomerName.setText(null);
            fldCustomerSurname.setText(null);
            fldCustomerPhoneNumber.setText(null);
            fldCustomerEmail.setText(null);
            fldCustomerAccountNumber.setText(null);

        }


    @Override
    public void initComponents() {



        try {
            fillTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        JLabel backgroundImage = new JLabel();
        backgroundImage.setIcon(Helper.createImage("src/javaProjeImage/customerLıst_v2.png"));

        fldId = new JFormattedTextField();
        fldId.setBounds(130, 70, 159,28);


        fldCustomerId = new JFormattedTextField();
        fldCustomerId.setBounds(125, 118, 159, 28);
        fldCustomerId.setEnabled(false);

        fldCustomerName = new JFormattedTextField();
        fldCustomerName.setBounds(125, 163, 159, 28);
        fldCustomerName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Helper.formatText(e, "character");
            }
        });

        fldCustomerSurname = new JFormattedTextField();
        fldCustomerSurname.setBounds(125, 208, 159, 28);
        fldCustomerSurname.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Helper.formatText(e, "character");
            }
        });

        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("(###) ### ## ##");
            maskFormatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }

        fldCustomerPhoneNumber = new JFormattedTextField(maskFormatter);
        fldCustomerPhoneNumber.setBounds(460, 70, 159, 28);
        fldCustomerPhoneNumber.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Helper.formatText(e, "number");
            }
        });

        fldCustomerEmail = new JFormattedTextField();
        fldCustomerEmail.setBounds(460, 118, 159, 28);


        fldCustomerAccountNumber = new JFormattedTextField();
        fldCustomerAccountNumber.setBounds(460, 163, 159, 28);
        fldCustomerAccountNumber.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Helper.formatText(e, "number");
            }
        });


        //TextField ların özelliklerini tek tek değiştirmektense aynı anda değiştirmek için array list içine attık.
        formattedTextFields.add(fldId);
        formattedTextFields.add(fldCustomerId);
        formattedTextFields.add(fldCustomerName);
        formattedTextFields.add(fldCustomerSurname);
        formattedTextFields.add(fldCustomerPhoneNumber);
        formattedTextFields.add(fldCustomerAccountNumber);
        formattedTextFields.add(fldCustomerEmail);

        for(JFormattedTextField textField : formattedTextFields){
            textField.setSize(new Dimension(159, 28));
            textField.setFont(Helper.write(15, 2));
            textField.setBorder(null);
            textField.setBackground(color);
        }

        fldId.setEnabled(false);





        btnSearch = new JButton("SEARCH");
        btnSearch.setBounds(70,550,105,55);
        btnSearch.setIcon(Helper.createImage("src\\javaProjeImage\\btnSearch.png"));
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = JOptionPane.showInputDialog(null, "Aramak istediğiniz kayıtın Id'si >", "Search Customer", JOptionPane.INFORMATION_MESSAGE);
                if(id != null){
                    if(id.equalsIgnoreCase("")){
                        JOptionPane.showMessageDialog(null, "Lütfen Id kısmını boş bırakmayınız!!!", "Uyarı", JOptionPane.ERROR_MESSAGE);
                    }else{
                        fldCustomerId.setText(String.valueOf(id));

                        if(fldCustomerId.getText().length() != 0){
                            try {
                                Customer customer;
                                customer = new Customer().searchCustomer(Integer.parseInt(fldCustomerId.getText()));
                                if(customer != null){
                                    fldCustomerName.setText(customer.getName());
                                    fldCustomerSurname.setText(customer.getSurname());
                                    fldCustomerPhoneNumber.setText(customer.getPhoneNumber());
                                    fldCustomerEmail.setText(customer.getEmail());
                                    fldCustomerAccountNumber.setText(customer.getAccountNumber());
                                }else{
                                    JOptionPane.showMessageDialog(null, "Müşteri Bulunamadı!!");
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

        btnUpdate = new JButton("UPDATE");
        btnUpdate.setBounds(215, 550, 105, 55);
        btnUpdate.setIcon(Helper.createImage("src\\javaProjeImage\\btnUpdate.png"));
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(
                        fldCustomerName.getText().length() == 0 ||
                                fldCustomerSurname.getText().length() == 0 ||
                                fldCustomerPhoneNumber.getText().length() == 0 ||
                                fldCustomerEmail.getText().length() == 0 ||
                                fldCustomerAccountNumber.getText().length() == 0
                ){
                    JOptionPane.showMessageDialog(null, "Lütfen Alanları boş bırakmayınız !!");
                }else{
                    if (Helper.checkEmail(fldCustomerEmail.getText())){
                        Customer customer = new Customer();
                        customer.setId(Integer.parseInt(fldCustomerId.getText()));
                        customer.setName(fldCustomerName.getText());
                        customer.setSurname(fldCustomerSurname.getText());
                        customer.setPhoneNumber(fldCustomerPhoneNumber.getText());
                        customer.setEmail(fldCustomerEmail.getText());
                        customer.setAccountNumber(fldCustomerAccountNumber.getText());

                        try {
                            new Customer().Update(customer);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        JOptionPane.showMessageDialog(null,  customer.getName() + " Adlı müşteri başarıyla güncellendi.");
                        try {
                            updateTableModel();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        clearField();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "GEÇERLİ BİR EMAİL ADRESİ GİRİNİZ!");
                    }
                }
            }
        });

        btnRemove = new JButton("REMOVE");
        btnRemove.setBounds(431, 550, 105, 55);
        btnRemove.setIcon(Helper.createImage("C:\\JavaClass\\ProjeEx\\src\\javaProjeImage\\btnRemove.png"));
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = JOptionPane.showInputDialog(null, "Silmek istediğiniz kayıtın Id'si >", "Remove Customer", JOptionPane.INFORMATION_MESSAGE);
                if(id != null){
                    if(id.equalsIgnoreCase("")){
                        JOptionPane.showMessageDialog(null, "Lütfen Id kısmını boş bırakmayınız!!!", "Uyarı", JOptionPane.ERROR_MESSAGE);
                    }else{
                        fldCustomerId.setText(String.valueOf(id));
                        if(fldCustomerId.getText().length() == 0){
                            JOptionPane.showMessageDialog(null, "Lütfen Alanları boş bırakmayınız !!");
                        }else{
                            try {
                                Customer customer;
                                customer = new Customer().searchCustomer(Integer.parseInt(fldCustomerId.getText()));
                                if(customer!= null){
                                    new Customer().delete(Integer.parseInt(fldCustomerId.getText()));
                                    JOptionPane.showMessageDialog(null, "Kayıt Silindi");
                                    clearField();
                                }else{
                                    JOptionPane.showMessageDialog(null, "MÜŞTERİ BULUNAMADI");
                                }
                                updateTableModel();
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

        btnCancel = new JButton("CANCEL");
        btnCancel.setBounds(576, 550, 105, 55);
        btnCancel.setIcon(Helper.createImage("C:\\JavaClass\\ProjeEx\\src\\javaProjeImage\\btnCancel.png"));
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearField();
            }
        });

        jButtons.add(btnSearch);
        jButtons.add(btnUpdate);
        jButtons.add(btnRemove);
        jButtons.add(btnCancel);

        for(JButton button : jButtons){
            button.setBorder(null);
            button.setSize(new Dimension(105,55));
            button.setForeground(color3);
            button.setBackground(color2);
            button.setFocusable(false);
            button.setVerticalTextPosition(JButton.TOP);
            button.setHorizontalTextPosition(JButton.CENTER);
            button.setFont(Helper.write(18, 1));
        }


        this.add(fldId);
        this.add(fldCustomerId);
        this.add(fldCustomerName);
        this.add(fldCustomerSurname);
        this.add(fldCustomerPhoneNumber);
        this.add(fldCustomerAccountNumber);
        this.add(fldCustomerEmail);
        this.add(btnSearch);
        this.add(btnUpdate);
        this.add(btnRemove);
        this.add(btnCancel);
        this.add(backgroundImage);


        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setTitle("Customer List");
        this.setSize(760,670);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
