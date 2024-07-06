import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PrescriptionManagementPage extends JFrame {
    private JTextField[] textFields;

    public PrescriptionManagementPage(JFrame parentFrame) {
        // Set the title of the JFrame
        setTitle("Medical/Prescription Records");

        // Default close operation
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Size of the JFrame
        setSize(800, 600);

        // Set the layout manager
        setLayout(new BorderLayout());

        // Panel for the logo and title
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(15, 107, 107));

        // Logo label
        ImageIcon logoIcon = new ImageIcon("images/Zahralogo.png"); // Replace with the correct path to your logo image
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Adjust the size as needed
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        topPanel.add(logoLabel);

        // Create title label
        JLabel titleLabel = new JLabel("Patient Receipt & Details");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        titleLabel.setForeground(new Color(250, 250, 250));
        topPanel.add(titleLabel);

        // Top panel to the north
        add(topPanel, BorderLayout.NORTH);

        // Panel for the form fields
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(15, 107, 107)); // Adjust the background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Text fields for form input
        textFields = new JTextField[18];
        for (int i = 0; i < textFields.length; i++) {
            textFields[i] = new JTextField(20);
        }

        // Added form fields with labels
        addFormField(formPanel, gbc, "Name of Tablets:", textFields[0], 0, 0);
        addFormField(formPanel, gbc, "Reference No.:", textFields[1], 0, 1);
        addFormField(formPanel, gbc, "Dose (mg):", textFields[2], 1, 0);
        addFormField(formPanel, gbc, "LOT:", textFields[3], 1, 1);
        addFormField(formPanel, gbc, "Issued Date:", textFields[4], 2, 0);
        addFormField(formPanel, gbc, "Daily Dose:", textFields[5], 2, 1);
        addFormField(formPanel, gbc, "Exp Date:", textFields[6], 3, 0);
        addFormField(formPanel, gbc, "Possible Side Effects:", textFields[7], 3, 1);
        addFormField(formPanel, gbc, "Further Information:", textFields[8], 4, 0);
        addFormField(formPanel, gbc, "Storage Advice:", textFields[9], 4, 1);
        addFormField(formPanel, gbc, "Driving and Using Machines:", textFields[10], 5, 0);
        addFormField(formPanel, gbc, "Patient ID:", textFields[11], 5, 1);
        addFormField(formPanel, gbc, "How to Use Medication:", textFields[12], 6, 0);
        addFormField(formPanel, gbc, "Health Card Number:", textFields[13], 6, 1);
        addFormField(formPanel, gbc, "Patient Name:", textFields[14], 7, 0);
        addFormField(formPanel, gbc, "Date of Birth:", textFields[15], 7, 1);
        addFormField(formPanel, gbc, "Patient Address:", textFields[16], 8, 0);

        // Added the form panel to the center of the frame
        add(formPanel, BorderLayout.CENTER);

        // Panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(15, 107, 107)); // Set the background color to match the top panel

        // Add buttons
        JButton saveButton = new RoundedButton("Save");
        JButton printButton = new RoundedButton("Print");
        JButton exitButton = new RoundedButton("Exit");

        // Added action listener to exit button to close the window and return to the main window
        exitButton.addActionListener(e -> {
            if (parentFrame != null) {
                parentFrame.setVisible(true);
            }
            dispose();
        });

        // Added action listener for the save button
        saveButton.addActionListener(e -> savePrescription());

        // Added action listener for the print button
        printButton.addActionListener(e -> {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setJobName("Print Prescription");

            job.setPrintable((graphics, pageFormat, pageIndex) -> {
                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2 = (Graphics2D) graphics;
                g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                // Scaled it to fit the content to the page
                double scaleX = pageFormat.getImageableWidth() / getWidth();
                double scaleY = pageFormat.getImageableHeight() / getHeight();
                double scale = Math.min(scaleX, scaleY);
                g2.scale(scale, scale);

                printAll(g2); // Use printAll instead of paint

                return Printable.PAGE_EXISTS;
            });

            boolean doPrint = job.printDialog();
            if (doPrint) {
                try {
                    job.print();
                } catch (PrinterException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(printButton);
        buttonPanel.add(exitButton);

        // Added the button panel to the bottom of the frame
        add(buttonPanel, BorderLayout.SOUTH);

        // Background color of the content pane
        getContentPane().setBackground(new Color(15, 107, 107)); // Adjust the background color
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JTextField textField, int row, int col) {
        gbc.gridx = col * 2;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = col * 2 + 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(textField, gbc);
    }

    private void savePrescription() {
        StringBuilder prescriptionData = new StringBuilder();
        prescriptionData.append("Name of Tablets: ").append(textFields[0].getText()).append("\n");
        prescriptionData.append("Reference No.: ").append(textFields[1].getText()).append("\n");
        prescriptionData.append("Dose (mg): ").append(textFields[2].getText()).append("\n");
        prescriptionData.append("Number of Tablets: ").append(textFields[3].getText()).append("\n");
        prescriptionData.append("LOT: ").append(textFields[4].getText()).append("\n");
        prescriptionData.append("Issued Date: ").append(textFields[5].getText()).append("\n");
        prescriptionData.append("Exp Date: ").append(textFields[6].getText()).append("\n");
        prescriptionData.append("Daily Dose: ").append(textFields[7].getText()).append("\n");
        prescriptionData.append("Possible Side Effects: ").append(textFields[8].getText()).append("\n");
        prescriptionData.append("Further Information: ").append(textFields[9].getText()).append("\n");
        prescriptionData.append("Storage Advice: ").append(textFields[10].getText()).append("\n");
        prescriptionData.append("Driving and Using Machines: ").append(textFields[11].getText()).append("\n");
        prescriptionData.append("How to Use Medication: ").append(textFields[12].getText()).append("\n");
        prescriptionData.append("Patient ID: ").append(textFields[13].getText()).append("\n");
        prescriptionData.append("NHS Number: ").append(textFields[14].getText()).append("\n");
        prescriptionData.append("Patient Name: ").append(textFields[15].getText()).append("\n");
        prescriptionData.append("Date of Birth: ").append(textFields[16].getText()).append("\n");
        prescriptionData.append("Patient Address: ").append(textFields[17].getText()).append("\n");

        try {
            File directory = new File("DataPrescriptions");
            if (!directory.exists()) {
            	directory.mkdir();
            }

            File file = new File(directory, "Prescription_" + System.currentTimeMillis() + ".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(prescriptionData.toString());
            writer.close();

            JOptionPane.showMessageDialog(this, "Prescription saved successfully at " + file.getAbsolutePath(), "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save prescription.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Testing purposes
        JFrame parentFrame = new JFrame(); // Create a dummy parent frame for testing
        PrescriptionManagementPage frame = new PrescriptionManagementPage(parentFrame);
        frame.setVisible(true);
    }
}
