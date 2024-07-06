import java.io.*;
import java.util.ArrayList;

public class DoctorDataHandler {
    private static final String DOCTORS_FILE = "doctors.dat";

    public static ArrayList<Doctor> loadDoctors() {
        ArrayList<Doctor> doctors = new ArrayList<>();
        File file = new File(DOCTORS_FILE);

        if (!file.exists()) {
            System.out.println("No existing doctor data found. A new file will be created.");
            saveDoctors(doctors); // Create the file with an empty list
            return doctors;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            doctors = (ArrayList<Doctor>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Doctor data file not found: " + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return doctors;
    }

    public static void saveDoctors(ArrayList<Doctor> doctors) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DOCTORS_FILE))) {
            oos.writeObject(doctors);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
