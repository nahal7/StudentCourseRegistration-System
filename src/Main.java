
import database.DatabaseInitializer;
import ui.LoginFrame;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        DatabaseInitializer.initialize();

        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
