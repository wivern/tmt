package net.anotheria.tmt;

import net.anotheria.tmt.utils.SpringUtilities;
import net.anotheria.tmt.widgets.Bulb;

import javax.swing.*;
import java.awt.*;

/**
 * @author VKoulakov
 * @since 03.06.14 14:05
 */
public class MainWindow extends JFrame {
    private SystemTray systemTray;

    public MainWindow() throws HeadlessException {
        setTitle("TMT");
        setSize(600, 450);
        setPreferredSize(new Dimension(600, 560));
        setLocationRelativeTo(null);

        if (java.awt.SystemTray.isSupported()) {
            systemTray = new SystemTray(this);
            setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        } else {
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }

        buildUI();
    }

    private void buildUI() {
        //top label
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel("Tunnel Management Tool");
        label.setAlignmentX(CENTER_ALIGNMENT);
        Font font = label.getFont();
        if (font != null){
            label.setFont(new Font(font.getName(), Font.BOLD, 14));
        }
//        topPanel.add(label);
//        topPanel.add(Box.createRigidArea(new Dimension(0, 32)));

        panel.add(topPanel, BorderLayout.PAGE_START);
        //form panel
        JPanel formBulbPanel = new JPanel();
        formBulbPanel.setLayout(new BoxLayout(formBulbPanel, BoxLayout.LINE_AXIS));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new SpringLayout());
        //status
        JLabel status = new JLabel("Status");
        JTextField statusText = new JTextField("", 15);
        statusText.setEditable(false);
        statusText.setMaximumSize(statusText.getPreferredSize());
        status.setLabelFor(statusText);
        formPanel.add(status);
        formPanel.add(statusText);
        //my ip
        JLabel myIP = new JLabel("My IP");
        JTextField myIPText = new JTextField("", 15);
        myIPText.setMaximumSize(myIPText.getPreferredSize());
        myIP.setLabelFor(myIPText);
        formPanel.add(myIP);
        formPanel.add(myIPText);
        //wan ip
        JLabel wanIP = new JLabel("WAN IP");
        JTextField wanIPText = new JTextField("", 15);
        Dimension size = wanIPText.getPreferredSize();
        size.setSize(10000, size.getHeight());
        wanIPText.setMaximumSize(size);
        wanIP.setLabelFor(myIPText);

        formPanel.add(wanIP);
        formPanel.add(wanIPText);
        SpringUtilities.makeCompactGrid(formPanel, 3, 2, 0, 5, 10, 5);

        formBulbPanel.add(formPanel);
        Bulb bulb = new Bulb();
        bulb.setMaximumSize(new Dimension(64, 64));
        formBulbPanel.add(bulb);

        topPanel.add(formBulbPanel);

        //Debug
        JPanel debugPanel = new JPanel();
        debugPanel.setLayout(new BoxLayout(debugPanel, BoxLayout.PAGE_AXIS));
        JLabel debug = new JLabel("Debug");
        JTextArea debugText = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(debugText);
        scrollPane.setAlignmentX(LEFT_ALIGNMENT);
        debug.setLabelFor(debugText);

        debugPanel.add(debug);
        debugPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        debugPanel.add(scrollPane);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        JButton refreshButton = new JButton("Refresh");

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 1));
        buttonPanel.add(Box.createGlue());
        buttonPanel.add(refreshButton);

        panel.add(debugPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.PAGE_END);

        getContentPane().add(panel);
        pack();

        bulb.setState(State.REFRESH_ON_SUCCESS);
        systemTray.setState(State.REFRESH_ON_SUCCESS);
    }
}
