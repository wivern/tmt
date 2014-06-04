package net.anotheria.tmt;

import net.anotheria.tmt.events.LocaleChangedEvent;
import net.anotheria.tmt.events.LocaleChangedEventListener;
import net.anotheria.tmt.events.StateChangedEvent;
import net.anotheria.tmt.events.StateChangedEventListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Locale;

/**
 * User: AAbushkevich
 * Date: 04.06.14
 * Time: 12:17
 */
public class SystemTray implements StateChangedEventListener, LocaleChangedEventListener {
    private JFrame window;
    private TrayIcon trayIcon;
    private JMenuItem restore, language, exit;
    private JMenu languagesSubmenu;
    private State state;

    private Image grey;
    private Image red;
    private Image green;

    private Timer timer;

    private boolean blink = false;
    private TMT tmt;

    public SystemTray(JFrame window, TMT tmt) {
        try {
            this.tmt = tmt;
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

    public void displayMessage(String text) {
        trayIcon.displayMessage(Resources.get("app.name.short"), text, TrayIcon.MessageType.INFO);
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

        final JPopupMenu popup = new JPopupMenu();

        restore = new JMenuItem();
        restore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.setVisible(true);
            }
        });
        popup.add(restore);

        languagesSubmenu = new JMenu();
        for(final Locale locale: Resources.getAvailableLocalizations()) {
            JMenuItem lang = new JMenuItem(locale.getDisplayName());
            lang.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tmt.setLocale(locale);
                }
            });
            languagesSubmenu.add(lang);
        }
        popup.add(languagesSubmenu);

        exit = new JMenuItem();
        exit.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        popup.add(exit);

        trayIcon = new TrayIcon(grey);
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    window.setVisible(true);
                } else if (e.isPopupTrigger()) {
                    popup.setInvoker(popup);
                    popup.setVisible(true);
                    popup.setLocation(e.getX() - (popup.getWidth() / 2), e.getY() - popup.getHeight());
                }
            }
        });

        try {
            tray.add(trayIcon);
        } catch (AWTException ex) {
            System.err.println("TrayIcon could not be added.");
        }

        refreshTextResources();
    }

    private void blinkStart() {
        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blink = !blink;
                refresh();
            }
        });
        timer.start();
    }

    private void blinkStop() {
        if(timer != null) {
            timer.stop();
        }
    }

    @Override
    public void stateChanged(StateChangedEvent event) {
        setState(event.getState());
    }

    @Override
    public void localeChanged(LocaleChangedEvent event) {
        refreshTextResources();
    }

    private void refreshTextResources() {
        restore.setText(Resources.get("app.open"));
        languagesSubmenu.setText(Resources.get("app.languages"));
        exit.setText(Resources.get("app.exit"));
        trayIcon.setToolTip(Resources.get("app.name.short"));
    }
}
