
package ui;

import repository.UserRepository;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class RegisterFrame extends JFrame {

    private final JTextField usernameField = new JTextField(18);
    private final JTextField firstNameField = new JTextField(18);
    private final JTextField lastNameField = new JTextField(18);
    private final JPasswordField passwordField = new JPasswordField(18);
    private final JPasswordField confirmPasswordField =
            new JPasswordField(18);

    private final UserRepository userRepository =
            new UserRepository();

    public RegisterFrame() {

        setTitle("Student Registration");
        setSize(450, 430);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(
                20, 25, 20, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 7, 7, 7);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel(
                "Create Student Account", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;

        addRow(panel, gbc, 1, "First name:", firstNameField);
        addRow(panel, gbc, 2, "Last name:", lastNameField);
        addRow(panel, gbc, 3, "Username:", usernameField);
        addRow(panel, gbc, 4, "Password:", passwordField);
        addRow(panel, gbc, 5, "Confirm password:",
                confirmPasswordField);

        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back to Login");

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(registerButton);
        buttons.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(buttons, gbc);

        registerButton.addActionListener(e -> registerStudent());

        backButton.addActionListener(e -> dispose());

        add(panel);
    }

    private void addRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label,
            JComponent field) {

        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void registerStudent() {

        char[] password = passwordField.getPassword();
        char[] confirmation = confirmPasswordField.getPassword();

        try {
            if (firstNameField.getText().isBlank()
                    || lastNameField.getText().isBlank()
                    || usernameField.getText().isBlank()
                    || password.length == 0) {

                JOptionPane.showMessageDialog(
                        this, "Please fill in all fields.");
                return;
            }

            if (!Arrays.equals(password, confirmation)) {
                JOptionPane.showMessageDialog(
                        this, "Passwords do not match.");
                return;
            }

            if (password.length < 8) {
                JOptionPane.showMessageDialog(
                        this,
                        "Password must contain at least 8 characters.");
                return;
            }

            userRepository.createStudent(
                    usernameField.getText(),
                    password,
                    firstNameField.getText(),
                    lastNameField.getText()
            );

            password = null; // Repository clears this array.

            JOptionPane.showMessageDialog(
                    this, "Account created successfully!");

            dispose();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this, ex.getMessage());

        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not create account. "
                            + "Check whether the username already exists.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);

        } finally {
            if (password != null) {
                Arrays.fill(password, '\0');
            }
            Arrays.fill(confirmation, '\0');
            passwordField.setText("");
            confirmPasswordField.setText("");
        }
    }
}
