import javax.swing.*;
import java.awt.*;
import java.io.*;

public class CartPage extends JFrame {
    private DefaultListModel<String> cartModel;
    private JList<String> cartList;
    private File cartFile = new File("cartItems.txt");
    private JLabel totalLabel;
    private double total;

    public CartPage(JFrame parentFrame) {
        // Initialize cart model and list
        cartModel = new DefaultListModel<>();
        cartList = new JList<>(cartModel);
        total = 0.0;
        
        // Initialize total label with initial value
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setForeground(Color.WHITE);

        // Load cart items from file
        loadCartItems();

        // Set JFrame properties
        setTitle("Cart");
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

        // Title label
        JLabel titleLabel = new JLabel("Cart");
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

        // Button panel for the back button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(15, 107, 107)); // Match the background color
        buttonPanel.add(backButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        // Add the top panel to the JFrame
        add(topPanel, BorderLayout.NORTH);

        // Main panel for cart items
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
        cartPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cartPanel.setBackground(new Color(15, 107, 107)); // Background color for cart panel

        // Configure cart items list
        cartList.setFont(new Font("Arial", Font.BOLD, 18));
        cartList.setForeground(Color.WHITE);
        cartList.setBackground(new Color(15, 107, 107));

        // Add cart items list to a scroll pane
        JScrollPane scrollPane = new JScrollPane(cartList);
        cartPanel.add(scrollPane);

        // Add "Remove Selected" button
        RoundedButton removeButton = new RoundedButton("Remove Selected");
        removeButton.setFont(new Font("Arial", Font.BOLD, 14));
        removeButton.setPreferredSize(new Dimension(200, 40));
        removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeButton.addActionListener(e -> {
            int selectedIndex = cartList.getSelectedIndex();
            if (selectedIndex != -1) {
                String selectedItem = cartModel.getElementAt(selectedIndex);
                double itemPrice = extractPrice(selectedItem);
                total -= itemPrice;
                cartModel.remove(selectedIndex);
                updateTotal();
                saveCartItems();
                JOptionPane.showMessageDialog(cartPanel, "Selected item removed from cart.");
            } else {
                JOptionPane.showMessageDialog(cartPanel, "Please select an item to remove.");
            }
        });

        // Add space and "Remove Selected" button to the cart panel
        cartPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add space between list and button
        cartPanel.add(removeButton);

        // Add "Proceed to Checkout" button
        RoundedButton checkoutButton = new RoundedButton("Proceed to Checkout");
        checkoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        checkoutButton.setPreferredSize(new Dimension(200, 40));
        checkoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkoutButton.addActionListener(e -> {
            CheckoutPage checkoutPage = new CheckoutPage(this, total * 1.13); // Pass total with tax
            checkoutPage.setVisible(true);
            setVisible(false);
        });

        // Add space and "Proceed to Checkout" button to the cart panel
        cartPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add space between remove button and checkout button
        cartPanel.add(checkoutButton);

        // Add space and total label to the cart panel
        cartPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add space between checkout button and total label
        cartPanel.add(totalLabel);

        // Add the cart panel to the center of the JFrame
        add(cartPanel, BorderLayout.CENTER);

        // Set the background color for the content pane
        getContentPane().setBackground(new Color(15, 107, 107));
    }

    // Method to add an item to the cart
    public void addItemToCart(String item, double price) {
        cartModel.addElement(item);
        total += price;
        updateTotal();
        saveCartItems();
    }

    // Method to update the total cost label
    private void updateTotal() {
        double totalWithTax = total * 1.13; // Adding 13% tax
        totalLabel.setText(String.format("Total: $%.2f (Tax included)", totalWithTax));
    }

    // Method to extract the price from a cart item string
    private double extractPrice(String item) {
        String[] parts = item.split("\\$");
        return Double.parseDouble(parts[1]);
    }

    // Method to save cart items to a file
    private void saveCartItems() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(cartFile))) {
            for (int i = 0; i < cartModel.size(); i++) {
                writer.println(cartModel.getElementAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load cart items from a file
    private void loadCartItems() {
        if (cartFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(cartFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    cartModel.addElement(line);
                    total += extractPrice(line);
                }
                updateTotal();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Main method to run the CartPage for testing
    public static void main(String[] args) {
        JFrame parentFrame = new JFrame(); // For testing purposes
        CartPage frame = new CartPage(parentFrame);
        frame.setVisible(true);
    }
}
