package ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

/**
 * The main application
 */
public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        // schedule a job for creating and showing this application's UI
        Container container = new Container();
        SwingUtilities.invokeLater(() -> {
            log.info("Starting Application");
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                log.error("Error setting look and feel", e);
            }
            container.createAndShowMainUI();
        });
    }
}
