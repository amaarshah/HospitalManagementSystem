import java.io.*;
import java.util.ArrayList;

public class PatientDataHandler {
    private static final String PATIENTS_FILE = "patients.dat";

    public static ArrayList<Patient> loadPatients() {
        ArrayList<Patient> patients = new ArrayList<>();
        File file = new File(PATIENTS_FILE);

        if (!file.exists()) {
            System.out.println("No existing patient data found. A new file will be created.");
            savePatients(patients); // Create the file with an empty list
            return patients;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            patients = (ArrayList<Patient>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Patient data file not found: " + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return patients;
    }

    public static void savePatients(ArrayList<Patient> patients) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATIENTS_FILE))) {
            oos.writeObject(patients);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
