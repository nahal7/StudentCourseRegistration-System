
package repository;
import security.PasswordUtil;
import java.util.Arrays;
import database.DBConnection;
import model.User;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    public User findByUsername(String username) {

        String sql = """
                SELECT user_id, username, password_hash,
                       first_name, last_name, role
                FROM users
                WHERE username = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    return new User(
                            result.getInt("user_id"),
                            result.getString("username"),
                            result.getString("password_hash"),
                            result.getString("first_name"),
                            result.getString("last_name"),
                            result.getString("role")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Could not find user by username.", e);
        }

        return null;
    }
    
public void createStudent(
        String username,
        char[] password,
        String firstName,
        String lastName) {

    String sql = """
            INSERT INTO users
                (username, password_hash, first_name,
                 last_name, role)
            VALUES (?, ?, ?, ?, 'STUDENT')
            """;

    try {
        if (username == null || username.isBlank()
                || password == null || password.length == 0
                || firstName == null || firstName.isBlank()
                || lastName == null || lastName.isBlank()) {

            throw new IllegalArgumentException(
                    "All fields are required.");
        }

        String passwordHash = PasswordUtil.hashPassword(password);

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username.trim());
            statement.setString(2, passwordHash);
            statement.setString(3, firstName.trim());
            statement.setString(4, lastName.trim());

            statement.executeUpdate();
        }

    } catch (SQLException e) {
        throw new RuntimeException(
                "Could not create student account. "
                + "The username may already exist.", e);

    } finally {
        if (password != null) {
            Arrays.fill(password, '\0');
        }
    }
}

public User authenticate(String username, char[] password) {

    try {
        if (username == null || username.isBlank()
                || password == null || password.length == 0) {
            return null;
        }

        User user = findByUsername(username.trim());

        if (user == null) {
            return null;
        }

        boolean valid = PasswordUtil.verifyPassword(
                password,
                user.getPasswordHash()
        );

        return valid ? user : null;

    } finally {
        if (password != null) {
            Arrays.fill(password, '\0');
        }
    }
}


}
