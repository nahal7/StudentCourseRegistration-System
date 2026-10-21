
package model;

public class Registration {

    private int registrationId;
    private int studentId;
    private int courseId;
    private String registrationDate;

    public Registration(int registrationId, int studentId,
                        int courseId, String registrationDate) {
        this.registrationId = registrationId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.registrationDate = registrationDate;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String toString() {
        return "Registration ID: " + registrationId
                + ", Student ID: " + studentId
                + ", Course ID: " + courseId
                + ", Date: " + registrationDate;
    }
}
