package net.anotheria.tmt;

import net.anotheria.tmt.config.Configuration;
import net.anotheria.tmt.config.ConfigurationException;
import net.anotheria.tmt.config.ConfigurationManager;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Resources.init();
                MainWindow window = new MainWindow();
                window.setVisible(true);

                try {
                    Configuration configuration = ConfigurationManager.getConfiguration();
                    TMT tmt = new TMT(configuration);
                    tmt.addStateChangedListener(window);
                    if (java.awt.SystemTray.isSupported()) {
                        final SystemTray systemTray = new SystemTray(window);
                        window.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                        window.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                systemTray.displayMessage(Resources.get("messages.still-running"));
                            }
                        });
                        tmt.addStateChangedListener(systemTray);
                    } else {
                        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    }
                    tmt.start();
                } catch (ConfigurationException e) {
                    JOptionPane.showMessageDialog(window, Resources.get("messages.config.file-read-fail"),
                            Resources.get("app.generic-error"), JOptionPane.ERROR_MESSAGE);
                    window.dispose();
                }
            }
        });
    }
}
