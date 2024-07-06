import javax.swing.*;
import javax.swing.border.EmptyBorder; // Import EmptyBorder class
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class PharmacyManagementPage extends JFrame {
    private JFrame parentFrame;
    private CartPage cartPage;

    public PharmacyManagementPage(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.cartPage = new CartPage(parentFrame);
        setTitle("Pharmacy Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 800);
        setLayout(new BorderLayout());

        // Top panel with title, logo, and back button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(15, 107, 107));

        // Logo label
        ImageIcon logoIcon = new ImageIcon("images/Zahralogo.png"); // Replace with the correct path to your logo image
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Adjust the size as needed
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        logoLabel.setHorizontalAlignment(JLabel.LEFT);
        logoLabel.setVerticalAlignment(JLabel.CENTER);
        topPanel.add(logoLabel, BorderLayout.WEST);

        // Title
        JLabel titleLabel = new JLabel("Pharmacy Management");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        titleLabel.setForeground(new Color(250, 250, 250));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // Back button
        RoundedButton backButton = new RoundedButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 12)); // Smaller font size
        backButton.setPreferredSize(new Dimension(80, 30)); // Adjust the size as needed
        backButton.addActionListener(e -> {
            if (parentFrame != null) {
                parentFrame.setVisible(true);
            }
            dispose();
        });

        // Cart button
        ImageIcon cartIcon = new ImageIcon("images/Cart.png"); // Replace with the correct path to your cart image
        Image cartImage = cartIcon.getImage().getScaledInstance(50, 25, Image.SCALE_SMOOTH); // Adjust the size as needed
        RoundedCartButton cartButton = new RoundedCartButton("Cart", new ImageIcon(cartImage));
        cartButton.setHorizontalTextPosition(SwingConstants.CENTER);
        cartButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        cartButton.setBackground(new Color(15, 107, 107));
        cartButton.setForeground(new Color(15, 107, 107));
        cartButton.setFont(new Font("Arial", Font.BOLD, 16));
        cartButton.setPreferredSize(new Dimension(120, 60)); // Adjust the size as needed
        cartButton.addActionListener(e -> {
            cartPage.setVisible(true);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(15, 107, 107)); // Match the background color
        buttonPanel.add(backButton);
        buttonPanel.add(cartButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Main panel for medicines
        JPanel medicinePanel = new JPanel();
        medicinePanel.setLayout(new GridLayout(5, 3, 20, 20)); // 3 medicines per row with spacing
        medicinePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        medicinePanel.setBackground(new Color(15, 107, 107)); // Background color for medicine panel

        // Adding individual medicines
        medicinePanel.add(createMedicineItem("images/1.png", "Tylenol", "Tylenol Rapid Ease", 10.99));
        medicinePanel.add(createMedicineItem("images/2.png", "Advil", "Classic Advil", 8.99));
        medicinePanel.add(createMedicineItem("images/3.png", "Nasal Spray", "Flonase Nasal Spray", 5.99));
        medicinePanel.add(createMedicineItem("images/4.png", "Kids Gravol", "Gravol for Kids", 6.99));
        medicinePanel.add(createMedicineItem("images/5.png", "Reactine", "Reactine Pills", 12.99));
        medicinePanel.add(createMedicineItem("images/6.png", "Band Aid", "Band Aid", 4.99));
        medicinePanel.add(createMedicineItem("images/7.png", "Aerius Allergy", "Aerius", 15.99));
        medicinePanel.add(createMedicineItem("images/8.png", "Vicks VapoRub", "For Nasal Congestion", 7.99));
        medicinePanel.add(createMedicineItem("images/9.png", "Buckly's", "Complete", 9.99));
        medicinePanel.add(createMedicineItem("images/10.png", "Pokemon BandAid", "Limited Edition", 4.49));
        medicinePanel.add(createMedicineItem("images/11.png", "Voltaren Gel", "For those Old Joints", 11.99));
        medicinePanel.add(createMedicineItem("images/12.png", "Polysporin", "Cream", 8.49));
        medicinePanel.add(createMedicineItem("images/13.png", "Aleve Night", "For Peaceful Sleep", 10.49));
        medicinePanel.add(createMedicineItem("images/14.png", "Gravol", "For Grown ups", 5.49));
        medicinePanel.add(createMedicineItem("images/15.png", "Hydrogen Peroxide", "Oinment", 3.99));

        // Scroll pane for medicines
        JScrollPane scrollPane = new JScrollPane(medicinePanel);
        add(scrollPane, BorderLayout.CENTER);

        // Background color for content pane
        getContentPane().setBackground(new Color(15, 107, 107));
    }

    private JPanel createMedicineItem(String imagePath, String title, String subtitle, double price) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(15, 107, 107)); // Background color

        // Image
        JLabel imageLabel = new JLabel();
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Increased image size for better visibility
        imageLabel.setIcon(new ImageIcon(image));
        panel.add(imageLabel);

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE); // Set title text color to white
        panel.add(titleLabel);

        // Subtitle
        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setForeground(Color.WHITE); // Set subtitle text color to white
        panel.add(subtitleLabel);

        // Price
        JLabel priceLabel = new JLabel(String.format("$%.2f", price));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        priceLabel.setForeground(Color.WHITE); // Set price text color to white
        panel.add(priceLabel);

        // Quantity control panel
        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        quantityPanel.setBackground(new Color(15, 107, 107)); // Match background color

        // Replace with plus and minus icons
        ImageIcon minusIcon = new ImageIcon("images/Minus.png");
        ImageIcon plusIcon = new ImageIcon("images/Plus.png");

        // Quantity label
        JLabel quantityLabel = new JLabel("0");
        quantityLabel.setFont(new Font("Arial", Font.BOLD, 18));
        quantityLabel.setForeground(Color.WHITE); // Set quantity text color to white

        JButton minusButton = new JButton(new ImageIcon(minusIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        minusButton.setOpaque(false);
        minusButton.setContentAreaFilled(false);
        minusButton.setBorderPainted(false);
        minusButton.setPreferredSize(new Dimension(50, 25));
        minusButton.addActionListener(e -> {
            // Decrease quantity logic
            int currentQuantity = Integer.parseInt(quantityLabel.getText());
            if (currentQuantity > 0) {
                quantityLabel.setText(String.valueOf(currentQuantity - 1));
            }
        });

        JButton plusButton = new JButton(new ImageIcon(plusIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        plusButton.setOpaque(false);
        plusButton.setContentAreaFilled(false);
        plusButton.setBorderPainted(false);
        plusButton.setPreferredSize(new Dimension(50, 25));
        plusButton.addActionListener(e -> {
            // Increase quantity logic
            int currentQuantity = Integer.parseInt(quantityLabel.getText());
            quantityLabel.setText(String.valueOf(currentQuantity + 1));
        });

        quantityPanel.add(minusButton);
        quantityPanel.add(quantityLabel);
        quantityPanel.add(plusButton);

        panel.add(quantityPanel);

        // Add to Cart button
        JPanel addToCartPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addToCartPanel.setBackground(new Color(15, 107, 107)); // Match background color
        RoundedButton addToCartButton = new RoundedButton("Add");
        addToCartButton.setPreferredSize(new Dimension(120, 30)); // Adjusted size for the button
        addToCartButton.addActionListener(e -> {
            // Action to add medicine to cart
            int quantity = Integer.parseInt(quantityLabel.getText());
            if (quantity > 0) {
                String item = title + " (" + quantity + ") - $" + (price * quantity);
                cartPage.addItemToCart(item, price * quantity);
                JOptionPane.showMessageDialog(panel, title + " (" + quantity + ") added to cart.");
                quantityLabel.setText("0"); // Reset quantity to 0 after adding to cart
            } else {
                JOptionPane.showMessageDialog(panel, "Please select a quantity before adding to cart.");
            }
        });
        addToCartPanel.add(addToCartButton);
        panel.add(addToCartPanel);

        return panel;
    }

    public static void main(String[] args) {
        JFrame parentFrame = new JFrame(); // For testing purposes
        PharmacyManagementPage frame = new PharmacyManagementPage(parentFrame);
        frame.setVisible(true);
    }
}

class RoundedButton extends JButton {
    public RoundedButton(String text) {
        super(text);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    public RoundedButton(String text, Icon icon) {
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
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        super.paintComponent(g2d);
        g2d.dispose();
    }
}

class RoundedCartButton extends RoundedButton {
    public RoundedCartButton(String text) {
        super(text);
    }

    public RoundedCartButton(String text, Icon icon) {
        super(text, icon);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
        super.paintComponent(g2d);
        g2d.dispose();
    }
}
