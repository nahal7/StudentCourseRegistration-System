
# Course Registration System
## Setup Instructions and Extension Report

### 1. Project Overview

The Course Registration System is a desktop application developed using Java Swing for the graphical user interface and SQLite for database storage. JDBC is used to communicate with the database.

The system supports student registration and login, course browsing, course registration and cancellation, and administrator course management.

### 2. Implemented Extension: Course Capacity and Enrollment Management

The system has been extended to support course capacity management and real-time enrollment counts.

The main enhancements are:

- **Course capacity:** Each course has a maximum number of students who can register.
- **Enrollment count:** The number of students registered for each course is calculated from the registration records.
- **Admin management:** Administrators can add courses, edit course details, change capacity, and control course availability.
- **Student dashboard:** Students can view available courses and their registered courses on separate pages, with Capacity and Enrolled columns.
- **Registration limit:** The registration process checks course availability and capacity before inserting a registration.
- **Data persistence:** Course details and registration records are stored in SQLite and remain available after restarting the application.

### 3. Relevant Code Changes

| File | Changes and responsibilities |
|---|---|
| `DatabaseInitializer.java` | Adds the `capacity` column to an existing database when it is missing, using a schema migration. The default capacity is 60. |
| `Course.java` | Adds `capacity` and `enrolled` fields, constructors, and getter/setter methods. |
| `CourseRepository.java` | Retrieves course capacity and calculates enrollment counts. Supports saving and updating capacity. |
| `RegistrationRepository.java` | Retrieves registered courses with capacity and enrollment details. Checks availability and capacity during registration. |
| `StudentDashboard.java` | Displays Available Courses and My Registrations as separate pages, with Capacity and Enrolled columns. |
| `AdminDashboard.java` | Displays course capacity and enrollment counts and provides an interface for editing course details and capacity. |
| `AdminRegistrationsFrame.java` | Displays student registration details to administrators. |

### 4. Design Decisions

**4.1 Database migration**

The existing SQLite database is updated without recreating the tables or deleting existing records. The initializer checks whether the `capacity` column exists before adding it.

**4.2 Calculated enrollment count**

Enrollment is calculated by counting records in the `registrations` table for each course. This avoids maintaining a separate enrollment counter that could become inconsistent with actual registrations.

**4.3 Separation of responsibilities**

The application separates its responsibilities into model, repository, database, and user-interface classes. The model represents course data, repositories handle database operations, and Swing frames provide the interface.

**4.4 User interface design**

The student dashboard uses `CardLayout` to show Available Courses and My Registrations one at a time. The administrator dashboard provides a table-based interface for managing courses.

**4.5 Capacity validation**

A course cannot accept a normal registration when it is unavailable or its enrollment count has reached its capacity. The administrator interface also checks that a proposed capacity is positive and not lower than the displayed enrollment count.

### 5. Setup Instructions

#### Prerequisites

- Java Development Kit (JDK)
- Visual Studio Code with Java support, or another Java IDE
- SQLite JDBC driver JAR

#### Project structure

The project should contain the following:

```text
CourseRegistrationSystem/
├── src/
│   ├── Main.java
│   ├── database/
│   ├── model/
│   ├── repository/
│   ├── security/
│   └── ui/
├── lib/
│   └── sqlite-jdbc-<version>.jar
├── student_registration.db
└── README.md
```

The database file and JDBC JAR must be available at the paths expected by the application.

#### Step 1: Open the project

Open the `CourseRegistrationSystem` folder in Visual Studio Code.

#### Step 2: Verify Java

Open the integrated terminal and execute:

```powershell
java -version
javac -version
```

Both commands should report an installed Java version.

#### Step 3: Compile the application

From the project root, execute:

```powershell
javac -cp "lib/*" -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
```

If compilation succeeds, the compiled classes will be placed in the `out` directory.

#### Step 4: Run the application

If `Main.java` is in the default package, execute:

```powershell
java -cp "out;lib/*" Main
```

If the main class belongs to a named package, use its fully qualified class name instead.

#### Step 5: Initialize the database

The main application should call `DatabaseInitializer.initialize()` during startup. The initializer creates missing tables and adds the capacity column when necessary.

Keep the SQLite database in the project root if the application uses:

```java
jdbc:sqlite:student_registration.db
```

#### Step 6: Test the extension

1. Log in as an administrator.
2. Add a course and specify its capacity.
3. Edit an existing course's capacity and verify the change.
4. Log in as a student and inspect Available Courses.
5. Register for a course and verify that Enrolled increases.
6. Open My Registrations and verify the displayed capacity and enrollment count.
7. Restart the application and verify that the data persists.

### 6. Conclusion

The extension improves the Course Registration System by introducing capacity-aware course registration and enrollment reporting. The existing SQLite database is migrated safely, enrollment counts are derived from registration records, and both student and administrator interfaces display the relevant information.

The implementation maintains separation between database access, application models, and graphical interfaces, making the system easier to maintain and extend.

**Note:** The administrator interface's capacity validation is a client-side check. Additional database transaction safeguards are advisable if simultaneous registration or administrative updates must be handled reliably.

