
package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {

        String usersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL UNIQUE,
                    password_hash TEXT NOT NULL,
                    first_name TEXT NOT NULL DEFAULT '',
                    last_name TEXT NOT NULL DEFAULT '',
                    role TEXT NOT NULL DEFAULT 'STUDENT'
                        CHECK (role IN ('STUDENT', 'ADMIN'))
                )
                """;

        String coursesTable = """
                CREATE TABLE IF NOT EXISTS courses (
                    course_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    course_code TEXT NOT NULL UNIQUE,
                    course_name TEXT NOT NULL,
                    credits INTEGER NOT NULL CHECK (credits > 0),
                    available INTEGER NOT NULL DEFAULT 1
                        CHECK (available IN (0, 1)),
                    capacity INTEGER NOT NULL DEFAULT 60
                        CHECK (capacity > 0)
                )
                """;

        String registrationsTable = """
                CREATE TABLE IF NOT EXISTS registrations (
                    registration_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    student_id INTEGER NOT NULL
                        REFERENCES users(user_id) ON DELETE CASCADE,
                    course_id INTEGER NOT NULL
                        REFERENCES courses(course_id) ON DELETE CASCADE,
                    registration_date TEXT NOT NULL
                        DEFAULT CURRENT_TIMESTAMP,
                    UNIQUE (student_id, course_id)
                )
                """;

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(usersTable);
            statement.execute(coursesTable);
            statement.execute(registrationsTable);

            // Add capacity to an existing courses table if needed.
            boolean capacityExists = false;

            try (ResultSet columns =
                         statement.executeQuery("PRAGMA table_info(courses)")) {

                while (columns.next()) {
                    if ("capacity".equalsIgnoreCase(
                            columns.getString("name"))) {
                        capacityExists = true;
                        break;
                    }
                }
            }

            if (!capacityExists) {
                statement.execute("""
                        ALTER TABLE courses
                        ADD COLUMN capacity INTEGER NOT NULL DEFAULT 60
                        CHECK (capacity > 0)
                        """);

                System.out.println("Added capacity column to courses.");
            }

            System.out.println("Database initialized successfully!");

        } catch (SQLException e) {
            System.out.println("Failed to initialize database.");
            e.printStackTrace();
        }
    }
}
