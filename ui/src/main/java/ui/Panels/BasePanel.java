package ui.Panels;

import pipeline.controller.PipelineController;
import util.FileWalker;
import util.ResourceUtils;
import util.StringConstants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public abstract class BasePanel extends JPanel {

    protected final static String NEW_LINE = "\n";
    protected final int IMAGE_SIZE = 180;
    protected final String LINE_BREAK = "-------------------------------";

    protected JFileChooser fileChooser;

    protected JButton openButton;
    protected JButton clearButton;
    protected JButton actionButton;

    protected JTextArea textArea;
    protected JPanel selectedImagesArea;

    protected JScrollPane selectedImagesAreaScrollPane;
    protected JScrollPane textAreaScrollPane;

    protected JPanel buttonPanel;

    protected java.util.List<File> imageFiles;
    protected PipelineController pipelineController;

    public BasePanel() {
        super(new BorderLayout());

        buttonPanel = new JPanel();

        fileChooser = fileChooser();

        textArea = new JTextArea(5,20);
        textArea.setMargin(new Insets(5,5,5,5));
        textArea.setEditable(false);
        textAreaScrollPane = new JScrollPane(textArea);

        selectedImagesArea = new JPanel(new FlowLayout());
        selectedImagesAreaScrollPane = new JScrollPane(selectedImagesArea);
        selectedImagesAreaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        selectedImagesAreaScrollPane.setVisible(false);

        pipelineController = new PipelineController();
    }

    protected static JFileChooser fileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        File baseDirectory = new File(ResourceUtils.getResourcePathAsString(StringConstants.ASSIGNMENT));
        fileChooser.setCurrentDirectory(baseDirectory);
        return fileChooser;
    }

    protected static JButton button(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        return button;
    }

    /**
     *
     * @param text
     * @param textArea
     */
    protected static void appendText(String text, JTextArea textArea) {
        textArea.append(text + NEW_LINE);
    }

    /**
     * Repaint the parent of the training panel.
     */
    protected void repaintParent() {
        this.getParent().repaint();
    }

    /**
     * Add thumbnails to the image area for the selected image files.
     * @param files The imageFiles.
     */
    protected void addThumbnailsToImageArea(java.util.List<File> files) {
        selectedImagesArea.removeAll();
        for (File file : files) {
            ImageIcon icon = new ImageIcon(file.getPath());

            // Resize icon
            Image img = icon.getImage();
            Image newimg = img.getScaledInstance(IMAGE_SIZE, IMAGE_SIZE,  java.awt.Image.SCALE_SMOOTH);
            ImageIcon newIcon = new ImageIcon(newimg);

            JLabel picture = new JLabel(newIcon);
            selectedImagesArea.add(picture);
            selectedImagesAreaScrollPane.setVisible(true);
        }
        repaintParent();
    }

    protected class OpenButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int returnVal = fileChooser.showOpenDialog(BasePanel.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                imageFiles = FileWalker.discoverFilesOnPath(file.getPath());
                addThumbnailsToImageArea(imageFiles);
                appendText("Selected: " + file.getPath(), textArea);
            } else {
                appendText("Open command cancelled by user", textArea);
            }
        }
    }

    protected class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            imageFiles.clear();
            selectedImagesArea.removeAll();
            appendText("Cleared selection", textArea);
            repaintParent();
        }
    }
}
