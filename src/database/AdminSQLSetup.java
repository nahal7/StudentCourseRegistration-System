import database.DBConnection;
import security.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.Scanner;

public class AdminSQLSetup {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Admin username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Admin first name: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Admin last name: ");
        String lastName = scanner.nextLine().trim();

        System.out.print("Admin password: ");
        char[] password = scanner.nextLine().toCharArray();

        String sql = """
                INSERT INTO users
                    (username, password_hash, first_name,
                     last_name, role)
                VALUES (?, ?, ?, ?, 'ADMIN')
                """;

        try {
            if (username.isBlank() || firstName.isBlank()
                    || lastName.isBlank() || password.length < 8) {
                System.out.println(
                        "Enter all details. Password needs 8+ characters.");
                return;
            }

            String passwordHash = PasswordUtil.hashPassword(password);

            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(1, username);
                statement.setString(2, passwordHash);
                statement.setString(3, firstName);
                statement.setString(4, lastName);

                statement.executeUpdate();

                System.out.println("Admin account created successfully!");
            }

        } catch (Exception e) {
            System.out.println(
                    "Could not create admin. The username may already exist.");
            e.printStackTrace();

        } finally {
            Arrays.fill(password, '\0');
            scanner.close();
        }
    }
}