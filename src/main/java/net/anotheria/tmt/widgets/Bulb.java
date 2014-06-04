package net.anotheria.tmt.widgets;

import net.anotheria.tmt.State;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author VKoulakov
 * @since 03.06.14 17:26
 */
public class Bulb extends JComponent implements ActionListener {

    private State state;
    private Timer timer;
    boolean blink = false;
    private BufferedImage grey;
    private BufferedImage red;
    private BufferedImage green;

    public Bulb() {
        this(State.NONE);
    }

    public Bulb(State state) {
        try {
            grey = ImageIO.read(ClassLoader.getSystemResourceAsStream("images/grey.png"));
            red = ImageIO.read(ClassLoader.getSystemResourceAsStream("images/red.png"));
            green = ImageIO.read(ClassLoader.getSystemResourceAsStream("images/green.png"));
        } catch (IOException ignored) {
        }

        setPreferredSize(new Dimension(64, 64));
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        if (state.equals(State.REFRESH_ON_SUCCESS) || state.equals(State.REFRESH_ON_FAILURE)){
            timer = new Timer(500, this);
            timer.start();
        } else if (timer != null) {
            timer.stop();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage icon = getStateImage(state);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(icon, 0, 0, null);
    }

    private BufferedImage getStateImage(State state) {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        blink = !blink;
        repaint();
    }
}
