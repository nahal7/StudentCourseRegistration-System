
package ui;

import model.User;
import repository.RegistrationRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminRegistrationsFrame extends JFrame {

    private final RegistrationRepository repository =
            new RegistrationRepository();

    private final DefaultTableModel tableModel =
            new DefaultTableModel(
                    new String[]{
                            "Student Name",
                            "Username",
                            "Course Code",
                            "Course Name",
                            "Registration Date"
                    }, 0) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

    public AdminRegistrationsFrame(User admin) {

        setTitle("Student Registrations");
        setSize(900, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title = new JLabel(
                "Student Registrations - " + admin.getFullName());
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JButton refreshButton = new JButton("Refresh");
        JButton closeButton = new JButton("Close");

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(
                12, 12, 12, 12));
        topPanel.add(title, BorderLayout.CENTER);
        topPanel.add(refreshButton, BorderLayout.EAST);

        JTable table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(closeButton);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> loadRegistrations());
        closeButton.addActionListener(e -> dispose());

        loadRegistrations();
    }

    private void loadRegistrations() {
        

        try {
            List<Object[]> registrations =
                    repository.findAllRegistrationDetails();

            tableModel.setRowCount(0);

            for (Object[] registration : registrations) {
                tableModel.addRow(registration);
            }

        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load student registrations.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
