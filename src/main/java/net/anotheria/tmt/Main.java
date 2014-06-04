package net.anotheria.tmt;

import net.anotheria.tmt.config.Configuration;
import net.anotheria.tmt.config.ConfigurationException;
import net.anotheria.tmt.config.ConfigurationManager;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow window = new MainWindow();
//                window.pack();
                window.setVisible(true);
                try {
                    Configuration configuration = ConfigurationManager.getConfiguration();
                } catch (ConfigurationException e) {
                    JOptionPane.showMessageDialog(window, "Configuration file is failed to read.",
                            "TMT Error", JOptionPane.ERROR_MESSAGE);
                    window.dispose();
                }
            }
        });
    }
}
