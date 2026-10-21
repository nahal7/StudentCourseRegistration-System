
package model;

public class Course {

    private int courseId;
    private String courseCode;
    private String courseName;
    private int credits;
    private boolean available;
    private int capacity;
    private int enrolled;

    // Existing constructor retained for compatibility
    public Course(int courseId, String courseCode, String courseName,
                  int credits, boolean available) {
        this(courseId, courseCode, courseName, credits,
                available, 60, 0);
    }

    // New constructor with capacity and enrolled count
    public Course(int courseId, String courseCode, String courseName,
                  int credits, boolean available,
                  int capacity, int enrolled) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.available = available;
        this.capacity = capacity;
        this.enrolled = enrolled;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrolled() {
        return enrolled;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setEnrolled(int enrolled) {
        this.enrolled = enrolled;
    }

    @Override
    public String toString() {
        return courseCode + " - " + courseName;
    }
}
