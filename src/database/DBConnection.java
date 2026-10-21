
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:sqlite:student_registration.db";

    
public static Connection getConnection()
        throws SQLException {

    System.out.println(
            "Database path: "
            + new java.io.File("student_registration.db")
                    .getAbsolutePath()
    );

    Connection connection =
            DriverManager.getConnection(URL);

    try (var statement = connection.createStatement()) {
        statement.execute("PRAGMA foreign_keys = ON");
    } catch (SQLException e) {
        connection.close();
        throw e;
    }

    return connection;
}

}
