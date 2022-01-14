package GUI;

import Interfaces.IFrame;
import com.helper.Helper;
import model.SellProduct;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Case extends JDialog implements IFrame {
    public Case() throws HeadlessException, SQLException {

        initComponents();
    }

    @Override
    public void initComponents() throws SQLException {
        JLabel backgroundImage = new JLabel();
        backgroundImage.setIcon(Helper.createImage("src//javaProjeImage//case.png"));

        JLabel lblTotalMoney = new JLabel("Total Money");
        lblTotalMoney.setBackground(Color.white);
        lblTotalMoney.setOpaque(false);
        lblTotalMoney.setBounds(200, 260, 150,80);
        lblTotalMoney.setFont(Helper.write(30,1));
        lblTotalMoney.setForeground(Helper.getColor(2));

        lblTotalMoney.setText(String.valueOf(new SellProduct().totalQuantity()));

        this.add(lblTotalMoney);
        this.add(backgroundImage);

        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
        this.setTitle("Case");
        this.setSize(new Dimension(486,390));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
