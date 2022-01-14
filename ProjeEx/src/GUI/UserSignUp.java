package GUI;

import Interfaces.IFrame;
import Interfaces.ITable;
import com.helper.HashString;
import com.helper.Helper;
import model.Authority;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserSignUp extends JDialog implements ITable, IFrame {
    private  JFormattedTextField fldUsername;
    private  JPasswordField fldPassword;
    private  JPasswordField fldPasswordAgain;
    private  JComboBox fldAuthority;
    private  ArrayList<JFormattedTextField> jFormattedTextFields = new ArrayList<>();
    private JButton btnSignUp;
    private JTable table_user;
    private DefaultTableModel tableModel;
    private JCheckBox checkBox1;

    public UserSignUp() throws HeadlessException, SQLException {
        initComponents();
    }

    @Override
    public void fillTable() throws SQLException {
        tableModel = new DefaultTableModel();
        String[] column = {"Id", "Username", "Password", "AuthorityId"};
        tableModel.setColumnIdentifiers(column);

        ArrayList<User> users = new User().getAll();
        String [] data = new String[4];

        for(int i = 0; i < users.size(); i++){
            data[0] = String.valueOf(users.get(i).getId());
            data[1] = String.valueOf(users.get(i).getUsername());
            data[2] = String.valueOf(users.get(i).getPassword());
            data[3] = String.valueOf(users.get(i).getAuthorityId());
            tableModel.addRow(data);
        }

        table_user = new JTable(tableModel);
        table_user.setRowHeight(25);
        table_user.setDefaultEditor(Object.class, null);
        table_user.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table_user.setSelectionBackground(Helper.getColor(1));
        table_user.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table_user.getTableHeader().setOpaque(false);
        table_user.getTableHeader().setBackground(Helper.getColor(6));
        table_user.setShowVerticalLines(false);
        table_user.getTableHeader().setBorder(null);
        table_user.setFont(new Font("Segoe UI", Font.BOLD, 15));
        table_user.setFocusable(false);
        table_user.getTableHeader().setForeground(Color.white);

        JScrollPane scrollPane = new JScrollPane(table_user, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(320, 120, 405, 300);
        this.add(scrollPane);
    }

    @Override
    public void updateTableModel() throws SQLException {
        DefaultTableModel clearModel = (DefaultTableModel) table_user.getModel();
        clearModel.setRowCount(0);
        ArrayList<User> users = new User().getAll();
        String [] data = new String[4];

        for(int i = 0; i < users.size(); i++){
            data[0] = String.valueOf(users.get(i).getId());
            data[1] = String.valueOf(users.get(i).getUsername());
            data[2] = String.valueOf(users.get(i).getPassword());
            data[3] = String.valueOf(users.get(i).getAuthorityId());
            tableModel.addRow(data);
        }

    }

    public void clearField(){
        fldUsername.setText(null);
        fldPassword.setText(null);
        fldPasswordAgain.setText(null);
        fldAuthority.setSelectedItem(null);
    }

    @Override
    public void initComponents() throws SQLException {


        fillTable();


        fldUsername = new JFormattedTextField();
        fldUsername.setBounds(75, 125, 159, 28);

        fldPassword = new JPasswordField();
        fldPassword.setBounds(75, 193, 159, 28);
        fldPassword.setSize(new Dimension(159, 28));
        fldPassword.setFont(Helper.write(15, 2));
        fldPassword.setBorder(null);
        fldPassword.setBackground(Helper.getColor(1));
        fldPassword.setEchoChar('*');

        checkBox1 = new JCheckBox();
        checkBox1.setBounds(250, 193, 25,25);
        checkBox1.setBorder(null);
        checkBox1.setFocusable(false);
        checkBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkBox1.isSelected()){
                    fldPassword.setEchoChar((char) 0);
                    fldPasswordAgain.setEchoChar((char) 0);
                }else{
                    fldPassword.setEchoChar('*');
                    fldPasswordAgain.setEchoChar('*');
                }
            }
        });

        fldPasswordAgain = new JPasswordField();
        fldPasswordAgain.setBounds(77, 260, 159, 28);
        fldPasswordAgain.setSize(new Dimension(159, 28));
        fldPasswordAgain.setFont(Helper.write(15, 2));
        fldPasswordAgain.setBorder(null);
        fldPasswordAgain.setBackground(Helper.getColor(1));
        fldPasswordAgain.setEchoChar('*');

        fldAuthority = new JComboBox<>();
        fldAuthority.setBounds(77, 328, 159, 28);
        fldAuthority.setBorder(null);
        fldAuthority.setFont(Helper.write(15, 2));
        fldAuthority.setBackground(Helper.getColor(1));

        for(Authority authority : new Authority().getAll()){
            fldAuthority.addItem(authority);
        }

        jFormattedTextFields.add(fldUsername);

        for (JFormattedTextField textField : jFormattedTextFields) {
            textField.setSize(new Dimension(159, 28));
            textField.setFont(Helper.write(15, 2));
            textField.setBorder(null);
            textField.setBackground(Helper.getColor(1));
        }

        btnSignUp = new JButton("SIGN UP");
        btnSignUp.setBorder(null);
        btnSignUp.setBounds(80, 415, 160, 50);

        btnSignUp.setForeground(Helper.getColor(5));
        btnSignUp.setBackground(Helper.getColor(6));
        btnSignUp.setFocusable(false);
        btnSignUp.setVerticalTextPosition(JButton.TOP);
        btnSignUp.setHorizontalTextPosition(JButton.CENTER);
        btnSignUp.setFont(Helper.write(30, 1));
        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fldUsername.getText().length() == 0 || fldPassword.getText().length() == 0 || fldPasswordAgain.getText().length() == 0 || fldAuthority.getSelectedItem() == null){
                    JOptionPane.showMessageDialog(null, "GEREKLİ ALANLARI DOLDURUNUZ!!");
                }else{
                    if(fldPassword.getText().equals(fldPasswordAgain.getText())){
                        User user = new User();
                        user.setUsername(fldUsername.getText());
                        try {
                            user.setPassword(HashString.toHexString(HashString.getHashString(fldPassword.getText())));
                        } catch (NoSuchAlgorithmException ex) {
                            ex.printStackTrace();
                        }
                        Authority casAuthority = (Authority) fldAuthority.getSelectedItem();
                        user.setAuthorityId(casAuthority.getId());
                        try {
                            new User().Insert(user);
                            JOptionPane.showMessageDialog(null, "Kayıt Gerçekleşti");
                            updateTableModel();
                            clearField();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "ŞİFRELER UYUŞMUYOR!!");
                    }
                }
            }
        });




        JLabel backgroundImage = new JLabel();
        backgroundImage.setIcon(Helper.createImage("src//javaProjeImage//userSignUp.png"));


        this.add(fldUsername);
        this.add(fldPassword);
        this.add(checkBox1);
        this.add(fldPasswordAgain);
        this.add(fldAuthority);
        this.add(btnSignUp);
        this.add(backgroundImage);

        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setSize(755, 530);
        this.setTitle("SIGN UP");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }
}
