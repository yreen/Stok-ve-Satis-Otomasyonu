package GUI;

import Interfaces.IFrame;
import com.helper.HashString;
import com.helper.Helper;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.instrument.ClassDefinition;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginScreen extends JFrame implements IFrame {
    private JTextField fldUsername;
    private JPasswordField fldPassword;
    private ArrayList<JPasswordField> jPasswordFields = new ArrayList<>();
    private JButton btnLogin;
    private JCheckBox checkBox1;
    public LoginScreen() throws HeadlessException, SQLException {
        initComponents();


    }


    @Override
    public void initComponents() throws SQLException {
        fldUsername = new JTextField();
        fldUsername.setBounds(88, 117, 179, 30);
        fldUsername.setFont(Helper.write(15, 2));
        fldUsername.setBorder(null);
        fldUsername.setBackground(Helper.getColor(1));


        fldPassword = new JPasswordField();
        fldPassword.setBounds(88, 193, 179, 30);

        checkBox1 = new JCheckBox();
        checkBox1.setBounds(290, 193, 25,25);
        checkBox1.setBorder(null);
        checkBox1.setFocusable(false);
        checkBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkBox1.isSelected()){
                    fldPassword.setEchoChar((char) 0);

                }else{
                    fldPassword.setEchoChar('*');

                }
            }
        });

        jPasswordFields.add(fldPassword);

        for (JPasswordField textField : jPasswordFields) {
            textField.setFont(Helper.write(15, 2));
            textField.setBorder(null);
            textField.setBackground(Helper.getColor(1));
            textField.setEchoChar('*');
        }

        btnLogin = new JButton();
        btnLogin.setText("LOGIN");
        btnLogin.setBorder(null);
        btnLogin.setBounds(95, 295, 160, 50);
        btnLogin.setForeground(Helper.getColor(5));
        btnLogin.setBackground(Helper.getColor(6));
        btnLogin.setFocusable(false);
        btnLogin.setFont(Helper.write(30, 1));
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fldUsername.getText().length() == 0 || fldPassword.getText().length() == 0){
                    JOptionPane.showMessageDialog(null, "GEREKLİ ALANLARI DOLDURUNUZ!!");
                }else{
                    User user = new User();
                    user.setUsername(fldUsername.getText());
                    try {
                        user.setPassword(HashString.toHexString(HashString.getHashString(fldPassword.getText())));
                    } catch (NoSuchAlgorithmException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        if(new User().checkUser(user)){
                            JOptionPane.showMessageDialog(null, "GİRİŞ BAŞARILI");
                            User user1 = new User().searchUser(fldUsername.getText(), HashString.toHexString(HashString.getHashString(fldPassword.getText())));
                            new AnaMenu(user1.getAuthorityId());
                            System.out.println(user1.getAuthorityId());
                            clearField();

                            LoginScreen.this.dispose();
                        }else{
                            JOptionPane.showMessageDialog(null, "KULLANICI BULUNAMADI");
                            clearField();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (NoSuchAlgorithmException ex) {
                        ex.printStackTrace();
                    }
                }
             /*new AnaMenu(1);
             LoginScreen.this.dispose();*/

            }
        });
        JLabel backgroundImage = new JLabel(
        );
        backgroundImage.setIcon(Helper.createImage("src//javaProjeImage//Login_Screen.png"));


        this.add(fldUsername);
        this.add(fldPassword);
        this.add(btnLogin);
        this.add(checkBox1);
        this.add(backgroundImage);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(831, 400);
        this.setTitle("Login");
        this.setResizable(false);

        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setIconImage(Helper.createImage("src/javaProjeImage/stock1.png").getImage());
    }

    public void clearField(){
        fldUsername.setText(null);
        fldPassword.setText(null);

    }
}
