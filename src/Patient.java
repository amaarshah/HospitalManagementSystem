import java.io.Serializable;

public class Patient implements Serializable {
    private String name;
    private int age;
    private String address;
    private String condition;
    private String attendanceDate;
    private Doctor assignedDoctor;

    public Patient(String name, int age, String address, String condition, String attendanceDate) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.condition = condition;
        this.attendanceDate = attendanceDate;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public Doctor getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(Doctor assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }

    @Override
    public String toString() {
        return name + " (" + age + ")";
    }
}
