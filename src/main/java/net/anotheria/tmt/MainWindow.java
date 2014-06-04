package net.anotheria.tmt;

import net.anotheria.tmt.events.StateChangedEvent;
import net.anotheria.tmt.events.StateChangedEventListener;
import net.anotheria.tmt.utils.SpringUtilities;
import net.anotheria.tmt.widgets.Bulb;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author VKoulakov
 * @since 03.06.14 14:05
 */
public class MainWindow extends JFrame implements StateChangedEventListener {
    private Bulb bulb;
    private JTextField statusText;
    private Map<State, String> stateStatusMap = new HashMap<State, String>() {{
        put(State.CONNECTED, Resources.get("state.connected"));
        put(State.DISCONNECTED, Resources.get("state.disconnected"));
        put(State.REFRESH_ON_SUCCESS, Resources.get("state.refresh-on-success"));
        put(State.REFRESH_ON_FAILURE, Resources.get("state.refresh-on-failure"));
        put(State.NONE, Resources.get("state.ip-not-provided"));
    }};

    public MainWindow() throws HeadlessException {
        setTitle(Resources.get("app.name.full"));
        setSize(600, 450);
        setPreferredSize(new Dimension(600, 560));
        setLocationRelativeTo(null);
        buildUI();
    }

    private void buildUI() {
        //top label
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));

        panel.add(topPanel, BorderLayout.PAGE_START);
        //form panel
        JPanel formBulbPanel = new JPanel();
        formBulbPanel.setLayout(new BoxLayout(formBulbPanel, BoxLayout.LINE_AXIS));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new SpringLayout());
        //status
        JLabel status = new JLabel(Resources.get("app.gui.status"));
        statusText = new JTextField("", 15);
        statusText.setEditable(false);
        statusText.setMaximumSize(statusText.getPreferredSize());
        status.setLabelFor(statusText);
        formPanel.add(status);
        formPanel.add(statusText);
        //my ip
        JLabel myIP = new JLabel(Resources.get("app.gui.my-ip"));
        JTextField myIPText = new JTextField("", 15);
        myIPText.setMaximumSize(myIPText.getPreferredSize());
        myIP.setLabelFor(myIPText);
        formPanel.add(myIP);
        formPanel.add(myIPText);
        //wan ip
        JLabel wanIP = new JLabel(Resources.get("app.gui.wan-ip"));
        JTextField wanIPText = new JTextField("", 15);
        Dimension size = wanIPText.getPreferredSize();
        size.setSize(10000, size.getHeight());
        wanIPText.setMaximumSize(size);
        wanIP.setLabelFor(myIPText);

        formPanel.add(wanIP);
        formPanel.add(wanIPText);
        SpringUtilities.makeCompactGrid(formPanel, 3, 2, 0, 5, 10, 5);

        formBulbPanel.add(formPanel);
        bulb = new Bulb();
        bulb.setMaximumSize(new Dimension(64, 64));
        formBulbPanel.add(bulb);

        topPanel.add(formBulbPanel);

        //Debug
        JPanel debugPanel = new JPanel();
        debugPanel.setLayout(new BoxLayout(debugPanel, BoxLayout.PAGE_AXIS));
        JLabel debug = new JLabel(Resources.get("app.gui.debug"));
        JTextArea debugText = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(debugText);
        scrollPane.setAlignmentX(LEFT_ALIGNMENT);
        debug.setLabelFor(debugText);

        debugPanel.add(debug);
        debugPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        debugPanel.add(scrollPane);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        JButton refreshButton = new JButton(Resources.get("app.gui.refresh"));

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 1));
        buttonPanel.add(Box.createGlue());
        buttonPanel.add(refreshButton);

        panel.add(debugPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.PAGE_END);

        getContentPane().add(panel);
        pack();
    }

    protected void setState(State state) {
        bulb.setState(state);
        statusText.setText(getStatusText(state));
    }

    private String getStatusText(State state) {
        return stateStatusMap.get(state);
    }

    @Override
    public void stateChanged(StateChangedEvent event) {
        setState(event.getState());
    }
}
