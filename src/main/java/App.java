import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * The main application
 */
public class App {
    Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(() -> {
            //Turn off metal's use of bold fonts
            UIManager.put("swing.boldMetal", Boolean.FALSE);
            createAndShowGUI();
        });
    }

    protected static JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel();
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = TabbedPaneDemo.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the event dispatch thread.
     */
    private static void createAndShowGUI() {
        // Create training and classification components
        JComponent trainingPanel = makeTextPanel("Training");
        JComponent classificationPanel = makeTextPanel("Classification");

        // Create a tabbed pane and add the components to it
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Training", null, trainingPanel, "The training section");
        tabbedPane.addTab("Classification", null, classificationPanel, "The classification section");
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        // Create a panel and add the tabbed pane to it
        JPanel panel = new JPanel(new GridLayout(1, 1));
        panel.add(tabbedPane, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(600, 450));

        // Create a frame and add the panel to it
        JFrame frame = new JFrame("Tabbed Pane Demo");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
