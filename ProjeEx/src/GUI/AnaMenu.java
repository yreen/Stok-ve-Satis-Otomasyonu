package GUI;

import Interfaces.IFrame;
import com.helper.Helper;
import model.Product;
import org.w3c.dom.html.HTMLLegendElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class AnaMenu extends JFrame implements ActionListener, IFrame {

    private JPanel panel;
    private JMenu customer, product, stock, sales, cashBox, report, user, exitBar;
    private JMenuItem customerIdentification, customerList, productManager, stockManager, productSelling,
            totalQuantity, customerReport, productReport, stockReport, caseReport, userSignUp, eXit;



    public AnaMenu(int authority) {
        initComponents();
        if(authority == 3){
            customer.hide();
            sales.hide();
            cashBox.hide();
            report.hide();
            user.hide();
        }
        else if(authority == 2){
            cashBox.hide();
            user.hide();
            product.hide();
            stock.hide();
            caseReport.hide();
            userSignUp.hide();

        }

    }


    public JMenuBar initMenuBar() {
        int fontSize = 18;
        int subMenuFontSize = 17;
        Color menuBarBackgroundColor = new Color(192,192,192);

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        menuBar.setBackground(menuBarBackgroundColor);

        customer = new JMenu();
        customer.setFont(Helper.write(fontSize, 1));
        customer.setIcon(Helper.createImage("src\\javaProjeImage\\customer.png"));
        customer.setText("Customer");
        customer.setVerticalTextPosition(JMenu.CENTER);
        customer.setHorizontalTextPosition(JMenu.RIGHT);
        customer.setIconTextGap(2);

        product = new JMenu();
        product.setFont(Helper.write(fontSize, 1));
        product.setIcon(Helper.createImage("src\\javaProjeImage\\product_64.png"));
        product.setText("Product");
        product.setHorizontalTextPosition(JMenu.RIGHT);
        product.setVerticalTextPosition(JMenu.CENTER);
        product.setIconTextGap(5);

        stock = new JMenu();
        stock.setFont(Helper.write(fontSize, 1));
        stock.setIcon(Helper.createImage("src\\javaProjeImage\\stock_64.png"));
        stock.setText("Stock");
        stock.setVerticalTextPosition(JMenu.CENTER);
        stock.setHorizontalTextPosition(JMenu.RIGHT);
        stock.setIconTextGap(0);

        sales = new JMenu();
        sales.setFont(Helper.write(fontSize, 1));
        sales.setIcon(Helper.createImage("src\\javaProjeImage\\sales_64.png"));
        sales.setText("Sale");
        sales.setVerticalTextPosition(JMenu.CENTER);
        sales.setHorizontalTextPosition(JMenu.RIGHT);
        sales.setIconTextGap(0);

        cashBox = new JMenu();
        cashBox.setFont(Helper.write(fontSize, 1));
        cashBox.setIcon(Helper.createImage("src\\javaProjeImage\\cashBox_64.png"));
        cashBox.setText("Case");
        cashBox.setVerticalTextPosition(JMenu.CENTER);
        cashBox.setHorizontalTextPosition(JMenu.RIGHT);
        cashBox.setIconTextGap(3);

        report = new JMenu();
        report.setFont(Helper.write(fontSize, 1));
        report.setIcon(Helper.createImage("src\\javaProjeImage\\report_48.png"));
        report.setText("Case");
        report.setVerticalTextPosition(JMenu.CENTER);
        report.setHorizontalTextPosition(JMenu.RIGHT);
        report.setIconTextGap(3);

        JLabel label = new JLabel("                 ");
        JLabel label1 = new JLabel("                   ");


        user = new JMenu();
        user.setFont(Helper.write(fontSize, 1));
        user.setIcon(Helper.createImage("src\\javaProjeImage\\user_48.png"));
        user.setText("User");
        user.setVerticalTextPosition(JMenu.CENTER);
        user.setHorizontalTextPosition(JMenu.RIGHT);
        user.setIconTextGap(3);

        exitBar = new JMenu();
        exitBar.setFont(Helper.write(fontSize, 1));
        exitBar.setIcon(Helper.createImage("src\\javaProjeImage\\exitBar_48.png"));
        exitBar.setVerticalTextPosition(JMenu.CENTER);
        exitBar.setHorizontalTextPosition(JMenu.RIGHT);
        exitBar.setIconTextGap(3);
        exitBar.addActionListener(this);

        /* SUBMENUS */

        customerIdentification = new JMenuItem();
        customerIdentification.setFont(Helper.write(subMenuFontSize, 1));
        customerIdentification.setText("Customer Identification");
        customerIdentification.addActionListener(this);

        customerList = new JMenuItem();
        customerList.setFont(Helper.write(subMenuFontSize, 1));
        customerList.setText("Customer List");
        customerList.addActionListener(this);

        customer.add(customerIdentification);
        customer.add(customerList);

        productManager = new JMenuItem();
        productManager.setFont(Helper.write(subMenuFontSize, 1));
        productManager.setText("Product Add, Remove and Update");
        productManager.addActionListener(this);

        product.add(productManager);

        stockManager = new JMenuItem();
        stockManager.setFont(Helper.write(subMenuFontSize, 1));
        stockManager.setText("Stock Add, Remove and Update");
        stockManager.addActionListener(this);

        stock.add(stockManager);

        productSelling = new JMenuItem();
        productSelling.setFont(Helper.write(subMenuFontSize, 1));
        productSelling.setText("Sell Product");
        productSelling.addActionListener(this);

        sales.add(productSelling);

        totalQuantity = new JMenuItem();
        totalQuantity.setFont(Helper.write(subMenuFontSize, 1));
        totalQuantity.setText("Total Quantity");
        totalQuantity.addActionListener(this);

        cashBox.add(totalQuantity);

        customerReport = new JMenuItem();
        customerReport.setFont(Helper.write(subMenuFontSize, 1));
        customerReport.setText("Customer Report");
        customerReport.addActionListener(this);

        productReport = new JMenuItem();
        productReport.setFont(Helper.write(subMenuFontSize, 1));
        productReport.setText("Product Report");
        productReport.addActionListener(this);

        stockReport = new JMenuItem();
        stockReport.setFont(Helper.write(subMenuFontSize, 1));
        stockReport.setText("Stock Report");
        stockReport.addActionListener(this);

        caseReport = new JMenuItem();
        caseReport.setFont(Helper.write(subMenuFontSize, 1));
        caseReport.setText("Sales Report");
        caseReport.addActionListener(this);

        report.add(customerReport);
        report.add(productReport);
        report.add(stockReport);
        report.add(caseReport);

        userSignUp = new JMenuItem();
        userSignUp.setFont(Helper.write(subMenuFontSize, 1));
        userSignUp.setText("Add New User");
        userSignUp.addActionListener(this);

        user.add(userSignUp);

        eXit = new JMenuItem();
        eXit.setFont(Helper.write(subMenuFontSize, 1));
        eXit.setText("Exit");
        eXit.addActionListener(this);

        exitBar.add(eXit);

        menuBar.add(customer);
        menuBar.add(product);
        menuBar.add(stock);
        menuBar.add(sales);
        menuBar.add(cashBox);
        menuBar.add(report);
        menuBar.add(label);
        menuBar.add(user);
        menuBar.add(label1);
        menuBar.add(exitBar);
        return menuBar;
    }

    @Override
    public void initComponents() {

        JLabel backGround = new JLabel();
        backGround.setIcon(Helper.createImage("src\\javaProjeImage\\Menu.png"));

        this.setJMenuBar(initMenuBar());
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
        this.setSize(830,650);
        this.setResizable(false);
        //this.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
        this.setTitle("STOK TAKIP OTOMASYONU");
        this.setLocationRelativeTo(null);
        this.add(backGround);
        this.setIconImage(Helper.createImage("src/javaProjeImage/AnaMenu1.psd").getImage());




    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == eXit){
            System.exit(0);
        }
        else if(e.getSource() == customerIdentification) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new CustomerIdentification();
                }
            });
        }
        else if(e.getSource() == customerList){
            new CustomerList();
        }
        else if(e.getSource() == productManager){
            try {
                new ProductMenu();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        else if(e.getSource() == stockManager){
            try {
                new StockMenu();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        else if(e.getSource() == productSelling){
            try {
                new SellProduct();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }else if(e.getSource() == totalQuantity){
            try {
                new Case();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("poo");
        }else if(e.getSource() == customerReport){
            try {
                new CustomerReport();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        else if(e.getSource() == productReport){
            try {
                new ProductReport();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        else if(e.getSource() == stockReport){
            try {
                new StockReport();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }else if(e.getSource() == caseReport){
            try {
                new SalesReport();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }else if(e.getSource() == userSignUp){
            try {
                new UserSignUp();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }



    }

}


