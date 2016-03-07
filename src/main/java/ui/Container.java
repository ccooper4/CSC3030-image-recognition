package ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ResourceUtils;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Main UI container
 */
public class Container {
    private static final Logger log = LoggerFactory.getLogger(Container.class);
    private TrainingPanel trainingPanel;
    private ClassificationPanel classificationPanel;

    public Container() {
        trainingPanel = new TrainingPanel();
        classificationPanel = new ClassificationPanel();
    }

    /**
     * Create an {@link ImageIcon} for a given file path
     * @param file  the file to the icon
     * @return      the Image Icon
     */
    public static ImageIcon createImageIcon(String file) {
        URL url = ResourceUtils.getResourcePathAsURL(file);
        if (url != null) {
            return new ImageIcon(url);
        } else {
            log.error("Couldn't find file: " + file);
            return null;
        }
    }

    /**
     * Create the GUI and show it. For thread safety,
     * this method should be invoked from the event dispatch thread.
     */
    public void createAndShowMainUI() {
        log.info("Creating UI components");

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
