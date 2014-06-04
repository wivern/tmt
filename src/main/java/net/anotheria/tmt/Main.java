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
                window.setVisible(true);
                SystemTray systemTray;

                try {
                    Configuration configuration = ConfigurationManager.getConfiguration();
                    TMT tmt = new TMT(configuration);
                    tmt.addStateChangedListener(window);
                    if (java.awt.SystemTray.isSupported()) {
                        systemTray = new SystemTray(window);
                        window.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                        tmt.addStateChangedListener(systemTray);
                    } else {
                        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    }
                    tmt.start();
                } catch (ConfigurationException e) {
                    JOptionPane.showMessageDialog(window, "Configuration file is failed to read.",
                            "TMT Error", JOptionPane.ERROR_MESSAGE);
                    window.dispose();
                }
            }
        });
    }
}
