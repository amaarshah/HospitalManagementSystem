import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PatientManagementPage extends JFrame {
    private JTextField nameField;
    private JTextField ageField;
    private JTextField addressField;
    private JTextField conditionField;
    private JFormattedTextField dateField;
    private JList<Patient> patientList;
    private DefaultListModel<Patient> listModel;
    private ArrayList<Patient> patients;

    public PatientManagementPage(JFrame parentFrame) {
        // Set the title of the JFrame
        setTitle("Patient Management");

        // Set the default close operation
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set the size of the JFrame
        setSize(800, 600);

        // Set the layout manager
        setLayout(new BorderLayout());

        // Create a panel for the logo and title
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(15, 107, 107));

        // Create logo label
        ImageIcon logoIcon = new ImageIcon("images/Zahralogo.png"); // Replace with the correct path to your logo image
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Adjust the size as needed
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        topPanel.add(logoLabel);

        // Create title label
        JLabel titleLabel = new JLabel("Patient Management");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        titleLabel.setForeground(new Color(250, 250, 250));
        topPanel.add(titleLabel);

        // Add the top panel to the north
        add(topPanel, BorderLayout.NORTH);

        // Create a panel for the form fields
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(15, 107, 107)); // Adjust the background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        nameField = new JTextField(20);
        ageField = new JTextField(20);
        addressField = new JTextField(20);
        conditionField = new JTextField(20);

        // Date field with date formatter
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateField = new JFormattedTextField(dateFormat);
        dateField.setColumns(20);
        dateField.setValue(new java.util.Date());

        // Add form fields with labels
        addFormField(formPanel, gbc, "Name:", nameField, 0, 0);
        addFormField(formPanel, gbc, "Age:", ageField, 1, 0);
        addFormField(formPanel, gbc, "Address:", addressField, 2, 0);
        addFormField(formPanel, gbc, "Condition:", conditionField, 3, 0);
        addFormField(formPanel, gbc, "Patient Attendance:", dateField, 4, 0);

        // Add the form panel to the center-left of the frame
        add(formPanel, BorderLayout.WEST);

        // Create a list model and JList for displaying patients
        listModel = new DefaultListModel<>();
        patientList = new JList<>(listModel);
        patientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(patientList);
        scrollPane.setPreferredSize(new Dimension(400, 500));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(scrollPane, BorderLayout.EAST);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(15, 107, 107)); // Set the background color to match the top panel

        // Add buttons
        JButton registerButton = new RoundedButton("Register");
        JButton updateButton = new RoundedButton("Update");
        JButton dischargeButton = new RoundedButton("Discharge");
        JButton backButton = new RoundedButton("Back");

        // Add action listeners to buttons
        registerButton.addActionListener(e -> registerPatient());
        updateButton.addActionListener(e -> updatePatient());
        dischargeButton.addActionListener(e -> dischargePatient());
        backButton.addActionListener(e -> {
            if (parentFrame != null) {
                parentFrame.setVisible(true);
            }
            dispose();
        });

        buttonPanel.add(registerButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(dischargeButton);
        buttonPanel.add(backButton);

        // Add the button panel to the bottom of the frame
        add(buttonPanel, BorderLayout.SOUTH);

        // Load patients from file
        patients = PatientDataHandler.loadPatients();
        displayPatients();

        // Set the background color of the content pane
        getContentPane().setBackground(new Color(15, 107, 107)); // Adjust the background color

        // Add list selection listener
        patientList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Patient selectedPatient = patientList.getSelectedValue();
                if (selectedPatient != null) {
                    nameField.setText(selectedPatient.getName());
                    ageField.setText(String.valueOf(selectedPatient.getAge()));
                    addressField.setText(selectedPatient.getAddress());
                    conditionField.setText(selectedPatient.getCondition());
                    dateField.setText(selectedPatient.getAttendanceDate());
                }
            }
        });
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JTextField textField, int row, int col) {
        gbc.gridx = col * 2;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        JLabel label = new JLabel(labelText);
        label.setForeground(new Color(250, 250, 250)); // Set the font color to white
        panel.add(label, gbc);

        gbc.gridx = col * 2 + 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(textField, gbc);
    }

    private void registerPatient() {
        if (isFormValid()) {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String address = addressField.getText();
            String condition = conditionField.getText();
            String attendanceDate = dateField.getText();
            Patient patient = new Patient(name, age, address, condition, attendanceDate);
            patients.add(patient);
            PatientDataHandler.savePatients(patients);
            displayPatients();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Form Incomplete", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePatient() {
        Patient selectedPatient = patientList.getSelectedValue();
        if (selectedPatient != null && isFormValid()) {
            selectedPatient.setName(nameField.getText());
            selectedPatient.setAge(Integer.parseInt(ageField.getText()));
            selectedPatient.setAddress(addressField.getText());
            selectedPatient.setCondition(conditionField.getText());
            selectedPatient.setAttendanceDate(dateField.getText());
            PatientDataHandler.savePatients(patients);
            displayPatients();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a patient to update and fill in all fields.", "No Selection or Incomplete Form", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void dischargePatient() {
        Patient selectedPatient = patientList.getSelectedValue();
        if (selectedPatient != null) {
            int response = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to discharge " + selectedPatient.getName() + "?",
                "Confirm Discharge",
                JOptionPane.YES_NO_OPTION
            );
            if (response == JOptionPane.YES_OPTION) {
                patients.remove(selectedPatient);
                PatientDataHandler.savePatients(patients);
                displayPatients();
                clearFields();
                JOptionPane.showMessageDialog(this, "Patient " + selectedPatient.getName() + " has been discharged.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a patient to discharge.", "No Selection", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isFormValid() {
        return !nameField.getText().isEmpty() &&
               !ageField.getText().isEmpty() &&
               !addressField.getText().isEmpty() &&
               !conditionField.getText().isEmpty() &&
               !dateField.getText().isEmpty();
    }

    private void displayPatients() {
        listModel.clear();
        for (Patient patient : patients) {
            listModel.addElement(patient);
        }
    }

    private void clearFields() {
        nameField.setText("");
        ageField.setText("");
        addressField.setText("");
        conditionField.setText("");
        dateField.setValue(null); // Clear the date field
    }

    public static void main(String[] args) {
        // For testing purposes
        JFrame parentFrame = new JFrame(); // Create a dummy parent frame for testing
        PatientManagementPage frame = new PatientManagementPage(parentFrame);
        frame.setVisible(true);
    }
}
