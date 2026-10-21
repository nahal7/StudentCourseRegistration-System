
package model;

public class Student extends User {

    public Student(int userId, String username, String passwordHash,
                   String firstName, String lastName) {

        super(userId, username, passwordHash,
              firstName, lastName, "STUDENT");
    }
}
