import java.io.Serializable;

public class Doctor implements Serializable {
    private String name;
    private int age;
    private String specialization;
    private int experience;

    public Doctor(String name, int age, String specialization, int experience) {
        this.name = name;
        this.age = age;
        this.specialization = specialization;
        this.experience = experience;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return name + " (" + specialization + ")";
    }
}
