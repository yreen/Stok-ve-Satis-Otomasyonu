import GUI.AnaMenu;
import GUI.LoginScreen;
import GUI.ProductJoinTable;

import javax.swing.*;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args){
        UIManager.getDefaults().put("TableHeader.cellBorder", BorderFactory.createEmptyBorder(0, 0, 0, 0));
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new LoginScreen();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });




    }
}
