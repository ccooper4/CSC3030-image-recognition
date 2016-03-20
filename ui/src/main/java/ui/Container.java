package ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.Panels.ClassificationPanel;
import ui.Panels.ResultPanel;
import ui.Panels.TrainingPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Main UI container
 */
public class Container {
    private static final Logger log = LoggerFactory.getLogger(Container.class);

    private TrainingPanel trainingPanel;
    private ClassificationPanel classificationPanel;
    private ResultPanel resultPanel;
    private ResultTableModel resultsModel;

    private final int MIN_WIDTH = 1300;
    private final int MIN_HEIGHT = 600;

    private final int TAB_WIDTH = 200;

    /**
     * Constructor.
     */
    public Container() {

        resultsModel = new ResultTableModel();

        trainingPanel = new TrainingPanel();
        classificationPanel = new ClassificationPanel(resultsModel);
        resultPanel = new ResultPanel(resultsModel);
    }

    /**
     * Create the GUI and show it. For thread safety,
     * this method should be invoked from the event dispatch thread.
     */
    public void createAndShowMainUI() {
        log.info("Creating UI components");

        // Create a tabbed pane and add the components to it
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab(getTabHTML("Training"), null, trainingPanel, "The training section");
        tabbedPane.addTab(getTabHTML("Classification"), null, classificationPanel, "The classification section");
        tabbedPane.addTab(getTabHTML("Results"), null, resultPanel, "The result section");
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        // Create a panel and add the tabbed pane to it
        JPanel panel = new JPanel(new GridLayout(1, 1));
        panel.add(tabbedPane, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));

        // Create a frame and add the panel to it
        JFrame frame = new JFrame("CSC3030 Assignment");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    private String getTabHTML(String title) {
        return "<html>" + "<body>" + "<table width='" + TAB_WIDTH + "'>" + title + "</table>" + "</body>" + "</html>";
    }
}
