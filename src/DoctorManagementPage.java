import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class DoctorManagementPage extends JFrame {
    private JTextField nameField;
    private JTextField ageField;
    private JTextField specializationField;
    private JTextField experienceField;
    private JTextArea doctorListArea;
    private JComboBox<String> doctorComboBox;
    private JComboBox<String> patientComboBox;
    private ArrayList<Doctor> doctors;
    private ArrayList<Patient> patients;

    public DoctorManagementPage(JFrame parentFrame) {
        // Set JFrame properties
        setTitle("Doctor Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Top panel with logo and title
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(15, 107, 107));

        // Logo label
        ImageIcon logoIcon = new ImageIcon("images/Zahralogo.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        topPanel.add(logoLabel);

        // Title label
        JLabel titleLabel = new JLabel("Doctor Management");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        titleLabel.setForeground(new Color(250, 250, 250));
        topPanel.add(titleLabel);

        // Add the top panel to the JFrame
        add(topPanel, BorderLayout.NORTH);

        // Form panel for input fields
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(15, 107, 107));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Initialize input fields
        nameField = new JTextField(20);
        ageField = new JTextField(20);
        specializationField = new JTextField(20);
        experienceField = new JTextField(20);

        // Add form fields to the form panel
        addFormField(formPanel, gbc, "Name:", nameField, 0, 0);
        addFormField(formPanel, gbc, "Age:", ageField, 1, 0);
        addFormField(formPanel, gbc, "Specialization:", specializationField, 2, 0);
        addFormField(formPanel, gbc, "Experience:", experienceField, 3, 0);

        // Add the form panel to the JFrame
        add(formPanel, BorderLayout.WEST);

        // Text area for displaying doctor list
        doctorListArea = new JTextArea(20, 20);
        doctorListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(doctorListArea);
        add(scrollPane, BorderLayout.CENTER);

        // Add mouse listener to the doctorListArea
        doctorListArea.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    try {
                        int offset = doctorListArea.viewToModel2D(e.getPoint());
                        int rowStart = Utilities.getRowStart(doctorListArea, offset);
                        int rowEnd = Utilities.getRowEnd(doctorListArea, offset);
                        String selectedText = doctorListArea.getText(rowStart, rowEnd - rowStart).trim();
                        for (Doctor doctor : doctors) {
                            if (selectedText.contains(doctor.getName())) {
                                populateFields(doctor);
                                break;
                            }
                        }
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Button panel for action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(15, 107, 107));

        // Action buttons
        JButton registerButton = new RoundedButton("Register");
        JButton updateButton = new RoundedButton("Update");
        JButton deleteButton = new RoundedButton("Delete");
        JButton backButton = new RoundedButton("Back");

        // Add action listeners to buttons
        registerButton.addActionListener(e -> registerDoctor());
        updateButton.addActionListener(e -> updateDoctor());
        deleteButton.addActionListener(e -> deleteDoctor());
        backButton.addActionListener(e -> {
            if (parentFrame != null) {
                parentFrame.setVisible(true);
            }
            dispose();
        });

        // Add buttons to the button panel
        buttonPanel.add(registerButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        // Add the button panel to the JFrame
        add(buttonPanel, BorderLayout.SOUTH);

        // Assign panel for assigning doctors to patients
        JPanel assignPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        assignPanel.setBackground(new Color(15, 107, 107));

        // Combo boxes for selecting doctor and patient
        doctorComboBox = new JComboBox<>();
        patientComboBox = new JComboBox<>();
        JButton assignButton = new RoundedButton("Assign Doctor to Patient");
        assignButton.addActionListener(e -> assignDoctor());

        // Add components to the assign panel
        assignPanel.add(new JLabel("Select Doctor:"));
        assignPanel.add(doctorComboBox);
        assignPanel.add(new JLabel("Select Patient:"));
        assignPanel.add(patientComboBox);
        assignPanel.add(assignButton);

        // Add the assign panel to the JFrame
        add(assignPanel, BorderLayout.NORTH);

        // Load doctors and patients from data handlers
        doctors = DoctorDataHandler.loadDoctors();
        patients = PatientDataHandler.loadPatients();

        // Update the doctor list and combo boxes
        updateDoctorList();
        updateComboBoxes();

        // Set the background color for the content pane
        getContentPane().setBackground(new Color(15, 107, 107));
    }

    // Method to add a form field to the panel
    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JTextField textField, int row, int col) {
        gbc.gridx = col * 2;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        JLabel label = new JLabel(labelText);
        label.setForeground(new Color(250, 250, 250));
        panel.add(label, gbc);
        gbc.gridx = col * 2 + 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(textField, gbc);
    }

    // Method to register a new doctor
    private void registerDoctor() {
        if (isFormValid()) {
            try {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText().replaceAll("[^\\d]", ""));
                String specialization = specializationField.getText();
                int experience = Integer.parseInt(experienceField.getText().replaceAll("[^\\d]", ""));

                Doctor doctor = new Doctor(name, age, specialization, experience);
                doctors.add(doctor);
                DoctorDataHandler.saveDoctors(doctors);
                updateDoctorList();
                updateComboBoxes();
                clearFields();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for age and experience.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Form Incomplete", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to update an existing doctor's information
    private void updateDoctor() {
        String name = nameField.getText();
        for (Doctor doctor : doctors) {
            if (doctor.getName().equals(name)) {
                try {
                    doctor.setAge(Integer.parseInt(ageField.getText().replaceAll("[^\\d]", "")));
                    doctor.setSpecialization(specializationField.getText());
                    doctor.setExperience(Integer.parseInt(experienceField.getText().replaceAll("[^\\d]", "")));
                    DoctorDataHandler.saveDoctors(doctors);
                    updateDoctorList();
                    updateComboBoxes();
                    clearFields();
                    return;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter valid numbers for age and experience.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(this, "Doctor not found.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Method to delete a doctor
    private void deleteDoctor() {
        String name = JOptionPane.showInputDialog(this, "Enter doctor's name to delete:");
        if (name != null) {
            int confirmed = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete Dr. " + name + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                boolean removed = doctors.removeIf(doctor -> doctor.getName().equals(name));
                if (removed) {
                    DoctorDataHandler.saveDoctors(doctors);
                    updateDoctorList();
                    updateComboBoxes();
                    JOptionPane.showMessageDialog(this, "Doctor " + name + " has been deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Doctor not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        clearFields();
    }

    // Method to assign a doctor to a patient
    private void assignDoctor() {
        String selectedDoctorName = (String) doctorComboBox.getSelectedItem();
        String selectedPatientName = (String) patientComboBox.getSelectedItem();

        Doctor selectedDoctor = doctors.stream()
                .filter(doctor -> doctor.getName().equals(selectedDoctorName))
                .findFirst().orElse(null);

        Patient selectedPatient = patients.stream()
                .filter(patient -> patient.getName().equals(selectedPatientName))
                .findFirst().orElse(null);

        if (selectedDoctor != null && selectedPatient != null) {
            selectedPatient.setAssignedDoctor(selectedDoctor);
            PatientDataHandler.savePatients(patients);
            JOptionPane.showMessageDialog(this, "Doctor " + selectedDoctor.getName() + " will be assisting patient " + selectedPatient.getName() + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Doctor or Patient not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to update the doctor list displayed in the text area
    private void updateDoctorList() {
        doctorListArea.setText("");
        for (Doctor doctor : doctors) {
            doctorListArea.append(doctor.getName() + " (" + doctor.getSpecialization() + ")\n");
        }
    }

    // Method to update the combo boxes with doctor and patient names
    private void updateComboBoxes() {
        doctorComboBox.removeAllItems();
        for (Doctor doctor : doctors) {
            doctorComboBox.addItem(doctor.getName());
        }
        patientComboBox.removeAllItems();
        for (Patient patient : patients) {
            patientComboBox.addItem(patient.getName());
        }
    }

    // Method to clear the input fields
    private void clearFields() {
        nameField.setText("");
        ageField.setText("");
        specializationField.setText("");
        experienceField.setText("");
    }

    // Method to validate that the form fields are filled
    private boolean isFormValid() {
        return !nameField.getText().isEmpty() &&
               !ageField.getText().isEmpty() &&
               !specializationField.getText().isEmpty() &&
               !experienceField.getText().isEmpty();
    }

    // Method to populate form fields with selected doctor's information
    private void populateFields(Doctor doctor) {
        nameField.setText(doctor.getName());
        ageField.setText(String.valueOf(doctor.getAge()));
        specializationField.setText(doctor.getSpecialization());
        experienceField.setText(String.valueOf(doctor.getExperience()));
    }

    // Main method to run the DoctorManagementPage for testing
    public static void main(String[] args) {
        JFrame parentFrame = new JFrame();
        DoctorManagementPage frame = new DoctorManagementPage(parentFrame);
        frame.setVisible(true);
    }
}
