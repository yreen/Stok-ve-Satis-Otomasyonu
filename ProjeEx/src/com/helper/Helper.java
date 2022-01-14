package com.helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.regex.Pattern;

public class Helper {

    public static ImageIcon createImage(String path){
        return new ImageIcon(path);
    }
    public static Font write(int fontSize, int n){
        switch (n){
            case 1:
                return new Font("Hindenburg", Font.ITALIC, fontSize);
            case 2:
                return new Font("Hindenburg", Font.PLAIN, fontSize);
        }
        return null;
    }
    public static Color getColor(int n){
        switch (n){
            case 1:
                return new Color(192,192,192);

            case 2:
                return new Color(79,79,79);
            case 3:
                return new Color(58,105,61);
            case 4:
                return new Color(219, 228,235);
            case 5:
                return Color.white;
            case 6:
                return new Color(74, 128, 77);
        }
        return null;
    }

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection("jdbc:sqlserver://ERENALP;Database=ProjectExDatabase", "test1", "database123");
            //return DriverManager.getConnection("jdbc:sqlserver://ERENALP;database=ProjectExDatabase;integratedSecurity=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void formatText(KeyEvent e, String str){
        char c;
        switch (str){
            case "number":
                c = e.getKeyChar();
                if(!Character.isDigit(c)){
                    e.consume();
                }
                break;
            case "character":
                c = e.getKeyChar();
                if(Character.isDigit(c)){
                    e.consume();
                }
                break;
        }
    }

    public static boolean checkEmail(String email){
        return Pattern.matches("^[a-zA-Z0-9]+[@]{1}+[a-zA-Z0-9]+[.]{1}+[a-zA-Z0-9]+$", email);
    }
}
