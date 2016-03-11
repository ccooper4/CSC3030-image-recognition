package ui;

import util.FileWalker;
import util.ResourceUtils;
import util.StringConstants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The panel used for training ui
 */
public class TrainingPanel extends JPanel {
    private final String NEW_LINE = "\n";

    private JFileChooser fileChooser;

    private JButton openButton;
    private JButton clearButton;
    private JButton trainButton;

    private JTextArea textArea;
    private JPanel imageArea;
    JScrollPane imageAreaScrollPane;

    private List<File> trainingFiles;

    private final int IMAGE_SIZE = 180;

    public TrainingPanel() {
        super(new BorderLayout());
        fileChooser = fileChooser();
        openButton = openButton();
        clearButton = clearButton();
        trainButton = trainButton();

        textArea = new JTextArea(5,20);
        textArea.setMargin(new Insets(5,5,5,5));
        textArea.setEditable(false);
        JScrollPane textAreaScrollPane = new JScrollPane(textArea);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(trainButton);

        imageArea = new JPanel(new FlowLayout());

        imageAreaScrollPane = new JScrollPane(imageArea);
        imageAreaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        imageAreaScrollPane.setVisible(false);

        add(buttonPanel, BorderLayout.NORTH);
        add(textAreaScrollPane, BorderLayout.CENTER);
        add(imageAreaScrollPane, BorderLayout.SOUTH);
    }

    private JFileChooser fileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        File baseDirectory = new File(ResourceUtils.getResourcePathAsString(StringConstants.ASSIGNMENT));
        fileChooser.setCurrentDirectory(baseDirectory);
        return fileChooser;
    }

    private JButton openButton() {
        JButton button = new JButton("Choose training image(s)");
        button.addActionListener(new OpenButtonListener());
        return button;
    }

    private JButton clearButton() {
        JButton button = new JButton("Clear Selection");
        button.addActionListener(new ClearButtonListener());
        return button;
    }

    private JButton trainButton() {
        JButton button = new JButton("Train System");
        button.addActionListener(new TrainButtonListener());
        return button;
    }

    private class OpenButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int returnVal = fileChooser.showOpenDialog(TrainingPanel.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                trainingFiles = FileWalker.discoverFilesOnPath(file.getPath());
                addThumbnailsToImageArea(trainingFiles);
                appendText("Selected: " + file.getPath());
            } else {
                appendText("Open command cancelled by user.");
            }
        }
    }

    private class TrainButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (trainingFiles != null && !trainingFiles.isEmpty()) {
                trainingFiles.forEach(f -> appendText("Training: " + f.getName()));
                Map<String, List<File>> directoryFileMap = new HashMap<>();
                trainingFiles.forEach(f -> f.getParentFile().getName());
            } else {
                appendText("No training images selected");
            }
        }
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            trainingFiles.clear();
            imageArea.removeAll();
            appendText("Cleared selection");
            repaintParent();
        }
    }

    /**
     * Append text to the text area followed by a new line.
     * @param text  The text to append.
     */
    private void appendText(String text) {
        textArea.append(text + NEW_LINE);
    }

    /**
     * Add thumbnails to the image area for the selected files.
     * @param files The files.
     */
    private void addThumbnailsToImageArea(List<File> files) {
        imageArea.removeAll();
        for (File file : files) {
            ImageIcon icon = new ImageIcon(file.getPath());

            // Resize icon
            Image img = icon.getImage();
            Image newimg = img.getScaledInstance(IMAGE_SIZE, IMAGE_SIZE,  java.awt.Image.SCALE_SMOOTH);
            ImageIcon newIcon = new ImageIcon(newimg);

            JLabel picture = new JLabel(newIcon);
            imageArea.add(picture);
            imageAreaScrollPane.setVisible(true);
        }
        repaintParent();
    }

    /**
     * Repaint the parent of the training panel.
     */
    private void repaintParent() {
        this.getParent().repaint();
    }
}
