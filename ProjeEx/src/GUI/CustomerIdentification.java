package GUI;

import Interfaces.IFrame;
import com.helper.Helper;
import model.Customer;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;


public class CustomerIdentification extends JDialog implements IFrame {

    private final Color color = new Color(192, 192, 192);
    private final Color color1 = new Color(74, 128, 77);
    private final Color color2 = new Color(179, 198, 192);
    private final Color color3 = new Color(219, 228, 150);
    private JFormattedTextField fldCustomerId;
    private JFormattedTextField fldCustomerName;
    private JFormattedTextField fldCustomerSurname;
    private JFormattedTextField fldCustomerPhoneNumber;
    private JFormattedTextField fldCustomerEmail;
    private JFormattedTextField fldCustomerAccountNumber;
    private JButton btnAdd;
    private JButton btnCancel;

    private final ArrayList<JFormattedTextField> formattedTextFields = new ArrayList<>();


    public CustomerIdentification() throws HeadlessException {
        initComponents();

    }

    @Override
    public void initComponents() {
        int fontSize = 20;
        String fontName = "Hindenburg";


        JLabel background = new JLabel();
        background.setIcon(Helper.createImage("src/javaProjeImage/customerIden.png"));
        background.setOpaque(false);


        fldCustomerName = new JFormattedTextField();
        fldCustomerName.setBounds(180, 200, 200, 36);
        fldCustomerName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Helper.formatText(e, "character");
            }
        });


        fldCustomerSurname = new JFormattedTextField();
        fldCustomerSurname.setBounds(180, 252, 200, 36);
        fldCustomerSurname.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Helper.formatText(e, "character");
            }
        });

        //Numara girişi için filtreleme işlemi yaptık.
        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("(###) ### ## ##");
            maskFormatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }

        fldCustomerPhoneNumber = new JFormattedTextField(maskFormatter);
        fldCustomerPhoneNumber.setBounds(180, 305, 200, 36);
        fldCustomerPhoneNumber.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Helper.formatText(e, "number");
            }
        });

        fldCustomerEmail = new JFormattedTextField();
        fldCustomerEmail.setBounds(180, 357, 200, 36);

        fldCustomerAccountNumber = new JFormattedTextField();
        fldCustomerAccountNumber.setBounds(180, 413, 200, 36);
        fldCustomerAccountNumber.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Helper.formatText(e, "number");
            }
        });

        //TextField ların özelliklerini tek tek değiştirmektense aynı anda değiştirmek için array list içine attık.
        formattedTextFields.add(fldCustomerName);
        formattedTextFields.add(fldCustomerSurname);
        formattedTextFields.add(fldCustomerPhoneNumber);
        formattedTextFields.add(fldCustomerEmail);
        formattedTextFields.add(fldCustomerAccountNumber);

        for (JFormattedTextField textField : formattedTextFields) {
            //textField.setSize(new Dimension(260, 45));
            textField.setFont(Helper.write(fontSize, 1));
            textField.setBorder(null);
            textField.setBackground(color);

        }


        // Ekleme ve İptal etme butonlarını oluşturup özellliklerini tanımladık.
        btnAdd = new JButton("Add Customer");
        btnAdd.setBounds(60, 535, 140, 40);
        btnAdd.setBackground(color1);
        btnAdd.setFont(Helper.write(fontSize - 5, 1));
        btnAdd.setIcon(Helper.createImage("src\\javaProjeImage\\CustomerAdd.png"));
        btnAdd.setHorizontalTextPosition(JButton.RIGHT);
        btnAdd.setVerticalTextPosition(JButton.CENTER);
        btnAdd.setFocusable(false);
        btnAdd.setForeground(color3);
        btnAdd.setBorder(null);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (
                        fldCustomerName.getText().length() == 0 ||
                                fldCustomerSurname.getText().length() == 0 ||
                                fldCustomerPhoneNumber.getText().length() == 0 ||
                                fldCustomerEmail.getText().length() == 0 ||
                                fldCustomerAccountNumber.getText().length() == 0
                ) {
                    JOptionPane.showMessageDialog(null, "Lütfen Alanları boş bırakmayınız !!");
                } else {
                    if (Helper.checkEmail(fldCustomerEmail.getText())) {
                        Customer customer = new Customer();
                        customer.setName(fldCustomerName.getText());
                        customer.setSurname(fldCustomerSurname.getText());
                        customer.setPhoneNumber(fldCustomerPhoneNumber.getText());
                        customer.setEmail(fldCustomerEmail.getText());
                        customer.setAccountNumber(fldCustomerAccountNumber.getText());

                        try {

                            new Customer().Insert(customer);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        JOptionPane.showMessageDialog(null, customer.getName() + " Adlı Müşteri Başarıyla Eklendi");
                        clearField();
                    } else {
                        JOptionPane.showMessageDialog(null, "GEÇERLİ BİR EMAİL ADRESİ GİRİNİZ!");
                    }

                }


            }
        });

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(250, 535, 140, 40);
        btnCancel.setBackground(color1);
        btnCancel.setFont(Helper.write(fontSize -5, 1));
        btnCancel.setIcon(Helper.createImage("src\\javaProjeImage\\btnCancel.png"));
        btnCancel.setHorizontalTextPosition(JButton.RIGHT);
        btnCancel.setVerticalTextPosition(JButton.CENTER);
        btnCancel.setFocusable(false);
        btnCancel.setForeground(color3);
        btnCancel.setBorder(null);


        this.add(fldCustomerName);
        this.add(fldCustomerSurname);
        this.add(fldCustomerPhoneNumber);
        this.add(fldCustomerEmail);
        this.add(fldCustomerAccountNumber);
        this.add(btnAdd);
        this.add(btnCancel);
        this.add(background);


        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setTitle("Customer Identification");
        this.setSize(new Dimension(431, 650));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public void clearField() {
        fldCustomerEmail.setText(null);
        fldCustomerName.setText(null);
        fldCustomerSurname.setText(null);
        fldCustomerAccountNumber.setText(null);
        fldCustomerPhoneNumber.setText(null);
    }


}
