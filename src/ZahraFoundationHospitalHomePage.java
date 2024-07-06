import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ZahraFoundationHospitalHomePage extends JFrame {
    public ZahraFoundationHospitalHomePage() {
        // Set the title of the JFrame
        setTitle("Zahra Foundation Hospital");

        // Set the default close operation to exit the application
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set the size of the JFrame
        setSize(1000, 800);

        // Set the layout manager
        setLayout(new BorderLayout());

        // Create a panel for the logo, text, and cart button
        JPanel topPanel = new JPanel(new BorderLayout());

        // Logo label
        ImageIcon logoIcon = new ImageIcon("images/Zahralogo.png"); // Replace with the correct path to your logo image
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Adjust the size as needed
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        logoLabel.setHorizontalAlignment(JLabel.LEFT);
        logoLabel.setVerticalAlignment(JLabel.CENTER);
        topPanel.add(logoLabel, BorderLayout.WEST);

        // Title Text label
        JLabel textLabel = new JLabel("Fatima Zahra Foundation");
        textLabel.setFont(new Font("Serif", Font.BOLD, 40)); // Increase the font size here
        textLabel.setForeground(new Color(250, 250, 250)); // Change to white for better visibility
        textLabel.setBorder(new EmptyBorder(0, 10, 0, 0)); // Add some padding to the left
        topPanel.add(textLabel, BorderLayout.CENTER);

        // Cart button
        ImageIcon cartIcon = new ImageIcon("images/Cart.png"); // Replace with the correct path to your cart image
        Image cartImage = cartIcon.getImage().getScaledInstance(50, 25, Image.SCALE_SMOOTH); // Adjust the size as needed
        RoundedCartButton cartButton = new RoundedCartButton("Cart", new ImageIcon(cartImage));
        cartButton.setHorizontalTextPosition(SwingConstants.CENTER);
        cartButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        cartButton.setBackground(new Color(15, 107, 107));
        cartButton.setForeground(new Color(250, 250, 250)); // Change to white for better visibility
        cartButton.setFont(new Font("Arial", Font.BOLD, 16));
        cartButton.setPreferredSize(new Dimension(120, 60)); // Adjust the size as needed
        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CartPage(ZahraFoundationHospitalHomePage.this).setVisible(true);
            }
        });

        // A panel to hold the cart button and align it to the right
        JPanel cartPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cartPanel.add(cartButton);
        cartPanel.setBackground(new Color(15, 107, 107)); // Set the same background color as the main content pane

        topPanel.add(cartPanel, BorderLayout.EAST);
        topPanel.setBackground(new Color(15, 107, 107)); // Set the same background color as the main content pane
        add(topPanel, BorderLayout.NORTH);

        // Rounded buttons for different sections of the hospital management system
        RoundedButton patientManagementButton = new RoundedButton("Patient Management");
        RoundedButton staffManagementButton = new RoundedButton("Doctor Management");
        RoundedButton pharmacyButton = new RoundedButton("Pharmacy");
        RoundedButton prescriptionManagementButton = new RoundedButton("Medical/Prescription Records");

        // Set font color and size for buttons
        patientManagementButton.setForeground(new Color(15, 107, 107));
        patientManagementButton.setFont(new Font("Arial", Font.BOLD, 20));
        staffManagementButton.setForeground(new Color(15, 107, 107));
        staffManagementButton.setFont(new Font("Arial", Font.BOLD, 20));
        pharmacyButton.setForeground(new Color(15, 107, 107));
        pharmacyButton.setFont(new Font("Arial", Font.BOLD, 20));
        prescriptionManagementButton.setForeground(new Color(15, 107, 107));
        prescriptionManagementButton.setFont(new Font("Arial", Font.BOLD, 20));

        // Action listeners
        patientManagementButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new PatientManagementPage(ZahraFoundationHospitalHomePage.this).setVisible(true);
                setVisible(false);
            }
        });

        staffManagementButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DoctorManagementPage(ZahraFoundationHospitalHomePage.this).setVisible(true);
                setVisible(false);
            }
        });

        pharmacyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new PharmacyManagementPage(ZahraFoundationHospitalHomePage.this).setVisible(true);
                setVisible(false);
            }
        });

        prescriptionManagementButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new PrescriptionManagementPage(ZahraFoundationHospitalHomePage.this).setVisible(true);
                setVisible(false);
            }
        });

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBackground(new Color(15, 107, 107));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        buttonPanel.add(patientManagementButton);
        buttonPanel.add(staffManagementButton);
        buttonPanel.add(pharmacyButton);
        buttonPanel.add(prescriptionManagementButton);

        // Add the panel to the center of the frame
        add(buttonPanel, BorderLayout.CENTER);

        // Create a footer label
        JLabel footerLabel = new JLabel("Zahra Foundation Hospital - Excellence in Healthcare", JLabel.CENTER);
        footerLabel.setFont(new Font("Serif", Font.ITALIC, 18));
        footerLabel.setForeground(new Color(15, 107, 107));
        add(footerLabel, BorderLayout.SOUTH);

        // Set the background color of the content pane
        getContentPane().setBackground(new Color(250, 250, 250));
    }

    public static void main(String[] args) {
        // Create an instance of the home page and make it visible
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ZahraFoundationHospitalHomePage homePage = new ZahraFoundationHospitalHomePage();
                homePage.setVisible(true);
            }
        });
    }
}

// RoundedCartButton class
class RoundedCartButton extends JButton {
    public RoundedCartButton(String text, Icon icon) {
        super(text, icon);

        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        super.paintComponent(g2d);
        g2d.dispose();
    }
}

// RoundedButton class
class RoundedButton extends JButton {
    public RoundedButton(String text) {
        super(text);

        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        super.paintComponent(g2d);
        g2d.dispose();
    }
}
