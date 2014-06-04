package net.anotheria.tmt;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * User: AAbushkevich
 * Date: 04.06.14
 * Time: 12:17
 */
public class SystemTray implements ActionListener {
    private JFrame window;
    private TrayIcon trayIcon;
    private State state;

    private Image grey;
    private Image red;
    private Image green;

    private Timer timer;

    private boolean blink = false;

    public SystemTray(JFrame window) {
        try {
            this.window = window;
            grey = ImageIO.read(ClassLoader.getSystemResourceAsStream("images/grey16.png"));
            red = ImageIO.read(ClassLoader.getSystemResourceAsStream("images/red16.png"));
            green = ImageIO.read(ClassLoader.getSystemResourceAsStream("images/green16.png"));
            init();
        } catch (IOException ignored) {
        }
    }

    public void setState(State state) {
        this.state = state;
        if (state.equals(State.REFRESH_ON_SUCCESS) || state.equals(State.REFRESH_ON_FAILURE)){
            blinkStart();
        } else {
            blinkStop();
        }
        refresh();
    }

    private void refresh() {
        trayIcon.setImage(getStateImage(state));
    }

    private Image getStateImage(State state) {
        switch (state){
            case CONNECTED:
                return green;
            case DISCONNECTED:
                return red;
            case NONE:
                return grey;
            case REFRESH_ON_SUCCESS:
                return blink ? green : grey;
            case REFRESH_ON_FAILURE:
                return blink ? red : grey;
        }
        return null;
    }

    private void init() throws IOException {
        final java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();

        ActionListener exitListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        ActionListener restoreListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.setVisible(true);
            }
        };

        final JPopupMenu popup = new JPopupMenu();

        JMenuItem restore = new JMenuItem("Open");
        restore.addActionListener(restoreListener);
        popup.add(restore);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(exitListener);
        popup.add(exit);

        trayIcon = new TrayIcon(grey, "TMT");

        ActionListener actionListener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                trayIcon.displayMessage("Action Event",
                        "An Action Event Has Been Performed!",
                        TrayIcon.MessageType.INFO);
            }
        };

        trayIcon.addActionListener(actionListener);
        trayIcon.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popup.setLocation(e.getX(), e.getY());
                    popup.setInvoker(popup);
                    popup.setVisible(true);
                }
            }
        });

        try {
            tray.add(trayIcon);
        } catch (AWTException ex) {
            System.err.println("TrayIcon could not be added.");
        }
    }

    private void blinkStart() {
        timer = new Timer(500, this);
        timer.start();
    }

    private void blinkStop() {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        blink = !blink;
        refresh();
    }
}
