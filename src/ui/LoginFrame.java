
package ui;

import repository.UserRepository;
import model.User;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class LoginFrame extends JFrame {

    private final JTextField usernameField = new JTextField(18);
    private final JPasswordField passwordField = new JPasswordField(18);

    private final UserRepository userRepository =
            new UserRepository();

    public LoginFrame() {

        setTitle("Course Registration System - Login");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(
                20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 8, 10, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel(
                "Course Registration System",
                SwingConstants.CENTER);

        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(loginButton);
        buttons.add(registerButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(buttons, gbc);

        loginButton.addActionListener(e -> login());

        registerButton.addActionListener(e -> {
            RegisterFrame registerFrame = new RegisterFrame();
            registerFrame.setVisible(true);
        });

        add(panel);
    }

    private void login() {

        char[] password = passwordField.getPassword();

        try {
            var user = userRepository.authenticate(
                    usernameField.getText(), password);

            password = null; // authenticate() clears the array.

            if (user == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid username or password.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

           
            if ("STUDENT".equals(user.getRole())) {

                new StudentDashboard(user).setVisible(true);
                dispose();

           } else if ("ADMIN".equals(user.getRole())) {

                new AdminDashboard(user).setVisible(true);
                dispose();

             } else {
                    JOptionPane.showMessageDialog(
                     this, "Unknown user role.");

            }


        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Login failed because of a database or system error.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

        } finally {
            if (password != null) {
                Arrays.fill(password, '\0');
            }
            passwordField.setText("");
        }
    }
}
