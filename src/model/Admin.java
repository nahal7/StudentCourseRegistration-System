
package model;

public class Admin extends User {

    public Admin(int userId, String username, String passwordHash,
                 String firstName, String lastName) {

        super(userId, username, passwordHash,
              firstName, lastName, "ADMIN");
    }
}
