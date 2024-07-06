import javax.swing.*;
import java.awt.*;

public class CheckoutPage extends JFrame {
    private JTextField nameField;
    private JTextField cardNumberField;
    private JTextField expiryDateField;
    private JTextField cvcField;
    private JLabel totalLabel;
    private double totalAmount;

    // Constructor for the CheckoutPage
    public CheckoutPage(JFrame parentFrame, double totalAmount) {
        this.totalAmount = totalAmount;

        // Title, close program, size, and layout of the JFrame
        setTitle("Checkout");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        // Top panel with title
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(new Color(15, 107, 107));

        // Title label
        JLabel titleLabel = new JLabel("Checkout");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        titleLabel.setForeground(new Color(250, 250, 250));
        topPanel.add(titleLabel);

        // Add the top panel to the JFrame
        add(topPanel, BorderLayout.NORTH);

        // Main panel for form fields
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(15, 107, 107));

        // GridBagConstraints for form layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Initialize form fields
        nameField = new JTextField(20);
        cardNumberField = new JTextField(16);
        expiryDateField = new JTextField(4);
        cvcField = new JTextField(3);

        // Total amount label
        totalLabel = new JLabel(String.format("Total Amount: $%.2f", totalAmount));
        totalLabel.setForeground(Color.WHITE);

        // Add form fields to the form panel
        addFormField(formPanel, gbc, "Name:", nameField, 0);
        addFormField(formPanel, gbc, "Card Number:", cardNumberField, 1);
        addFormField(formPanel, gbc, "Expiry Date (MMYY):", expiryDateField, 2);
        addFormField(formPanel, gbc, "CVC:", cvcField, 3);

        // Add total label to the form panel
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(totalLabel, gbc);

        // Add the form panel to the JFrame
        add(formPanel, BorderLayout.CENTER);

        // Bottom panel with checkout button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(15, 107, 107));

        // Checkout button
        JButton checkoutButton = new RoundedButton("Checkout");
        checkoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        checkoutButton.addActionListener(e -> {
            if (validateFields()) {
                JOptionPane.showMessageDialog(this, "Order completed");
                if (parentFrame != null) {
                    parentFrame.setVisible(true);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields correctly.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add the checkout button to the bottom panel
        buttonPanel.add(checkoutButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set background color for the content pane
        getContentPane().setBackground(new Color(15, 107, 107));
    }

    // Method to add a form field to the panel
    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JTextField textField, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.LINE_END;
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(textField, gbc);
    }

    // Method to validate form fields
    private boolean validateFields() {
        return !nameField.getText().isEmpty() &&
               cardNumberField.getText().matches("\\d{16}") &&
               expiryDateField.getText().matches("\\d{4}") &&
               cvcField.getText().matches("\\d{3}");
    }
}
