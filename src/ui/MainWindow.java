package ui;

import javax.swing.*;
import java.awt.event.*;
import model.HazardAlert;
import service.MessageStorage;
import service.AlertScheduler;
import exceptions.InvalidMessageException;

public class MainWindow extends JFrame {
    private JTextArea messageArea;
    private JTextField inputField;
    private JButton sendButton;
    private JButton scheduleButton;
    private JComboBox<String> typeDropdown;
    private JSpinner urgencySpinner;
    private JLabel statusLabel;

    public MainWindow() {
        setTitle("BlackoutNet Emergency Alert System");
        setSize(550, 650); // increased height for nodes section
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel inputLabel = new JLabel("Enter Alert Message:");
        inputLabel.setBounds(20, 20, 200, 25);
        add(inputLabel);

        inputField = new JTextField();
        inputField.setBounds(20, 50, 350, 25);
        add(inputField);

        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setBounds(20, 90, 100, 25);
        add(typeLabel);

        typeDropdown = new JComboBox<>(new String[] {"Flood", "Fire", "Medical", "Power", "Other"});
        typeDropdown.setBounds(70, 90, 150, 25);
        add(typeDropdown);

        JLabel urgencyLabel = new JLabel("Urgency (1-5):");
        urgencyLabel.setBounds(240, 90, 100, 25);
        add(urgencyLabel);

        urgencySpinner = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
        urgencySpinner.setBounds(340, 90, 50, 25);
        add(urgencySpinner);

        sendButton = new JButton("Send Alert");
        sendButton.setBounds(410, 90, 110, 25);
        add(sendButton);

        scheduleButton = new JButton("Schedule Alert");
        scheduleButton.setBounds(410, 130, 110, 25);
        add(scheduleButton);

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setBounds(20, 180, 500, 250);
        add(scrollPane);

        statusLabel = new JLabel(" ");
        statusLabel.setBounds(20, 450, 400, 25);
        add(statusLabel);

        // === Nearby Nodes Section ===
        JLabel nodesLabel = new JLabel("Nearby Nodes:");
        nodesLabel.setBounds(20, 480, 200, 25);
        add(nodesLabel);

        String[] nodes = {
            "Node-01 (Central Hospital)",
            "Node-02 (Shelter Zone A)",
            "Node-03 (Fire Dept.)",
            "Node-04 (Police HQ)",
            "Node-05 (Water Station)"
        };

        JList<String> nodeList = new JList<>(nodes);
        nodeList.setEnabled(false); // read-only
        JScrollPane nodeScroll = new JScrollPane(nodeList);
        nodeScroll.setBounds(20, 510, 500, 80);
        add(nodeScroll);

        // === Send Button Action ===
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String text = inputField.getText().trim();
                    if (text.isEmpty() || !text.matches(".*[a-zA-Z]+.*")) {
                        throw new InvalidMessageException("Alert must contain valid words (not just numbers or symbols).");
                    }

                    String type = (String) typeDropdown.getSelectedItem();
                    int urgency = (int) urgencySpinner.getValue();

                    HazardAlert alert = new HazardAlert("[" + type + "] " + text, urgency);
                    String display = alert.toString();
                    messageArea.append(display + "\n");
                    MessageStorage.saveMessage(display);

                    inputField.setText("");
                    statusLabel.setText("✅ Alert sent and saved.");
                } catch (InvalidMessageException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    statusLabel.setText("❌ Error: " + ex.getMessage());
                }
            }
        });

        // === Schedule Button Action ===
        scheduleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String text = inputField.getText().trim();
                    if (text.isEmpty()) {
                        throw new InvalidMessageException("Alert cannot be empty!");
                    }

                    String type = (String) typeDropdown.getSelectedItem();
                    int urgency = (int) urgencySpinner.getValue();

                    String delayInput = JOptionPane.showInputDialog("Delay in seconds:");
                    int delay = Integer.parseInt(delayInput);

                    statusLabel.setText("⏳ Alert scheduled...");

                    AlertScheduler.scheduleAlert(text, delay, () -> {
                        HazardAlert alert = new HazardAlert("[" + type + "] " + text, urgency);
                        String display = alert.toString();

                        SwingUtilities.invokeLater(() -> {
                            messageArea.append(display + "\n");
                            MessageStorage.saveMessage(display);
                            statusLabel.setText("✅ Scheduled alert sent.");
                        });
                    });

                } catch (InvalidMessageException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    statusLabel.setText("❌ Error: " + ex.getMessage());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid delay input.");
                    statusLabel.setText("❌ Invalid delay.");
                }
            }
        });

        setVisible(true);
    }
}
