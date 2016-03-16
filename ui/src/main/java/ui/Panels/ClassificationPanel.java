package ui.Panels;

import pipeline.featureextraction.FeaturePayload;
import util.FileWalker;
import util.ResourceUtils;
import util.StringConstants;
import util.image.ImageUtils;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * The panel used for classification UI
 */
public class ClassificationPanel extends BasePanel {

    private static final int imageSize = 120;
    private static final int processedImageSize = 180;

    private JPanel processedImagesArea;
    private JScrollPane processedImagesAreaScrollPane;
    private JTextArea resultField;

    private boolean histogramMode = false;
    private boolean classified = false;

    private int correctCount = 0;
    private int incorrectCount = 0;

    public ClassificationPanel() {
        super(imageSize);

        openButton = button("Choose test image(s)", new OpenButtonListener());
        clearButton = button("Reset Classification", new ClearButtonListener());
        actionButton = button("Classify", new ClassifyButtonListener());

        JToggleButton toggleButton = new JToggleButton("Histogram Mode");
        toggleButton.addActionListener(new ToggleButtonListener());

        JButton analysisButton = button("Analyse", new AnaylsisButtonListener());

        resultField = new JTextArea();
        resultField.setText("");
        resultField.setPreferredSize(new Dimension(50, 28));
        resultField.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
        resultField.setEditable(false);

        buttonPanel.add(openButton);
        buttonPanel.add(actionButton);
        buttonPanel.add(toggleButton);
        buttonPanel.add(analysisButton);
        buttonPanel.add(resultField);
        buttonPanel.add(clearButton);

        processedImagesArea = new JPanel();
        processedImagesArea.setLayout(new BoxLayout(processedImagesArea, BoxLayout.Y_AXIS));
        processedImagesAreaScrollPane = new JScrollPane(processedImagesArea);
        processedImagesAreaScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel middleSection = new JPanel(new BorderLayout());
        middleSection.add(textAreaScrollPane, BorderLayout.NORTH);
        middleSection.add(processedImagesAreaScrollPane, BorderLayout.CENTER);

        add(buttonPanel, BorderLayout.NORTH);
        add(middleSection, BorderLayout.CENTER);
        add(selectedImagesAreaScrollPane, BorderLayout.SOUTH);
    }

    private class ClassifyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (imageFiles != null && !imageFiles.isEmpty()) {
                appendText("Performing classification", textArea);
                drawProcessedImagePanel();
                classified = true;
            } else {
                appendText("No test images selected", textArea);
            }
        }
    }

    private class AnaylsisButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (classified) {
                if (incorrectCount + correctCount < imageFiles.size()) {
                    JOptionPane.showMessageDialog(ClassificationPanel.this, "Please specify correctness on all results");
                } else {
                    float recognitionRate = (float) correctCount / (float) imageFiles.size();
                    int percentage = (int) (recognitionRate * 100);
                    resultField.setText(percentage + "%");
                }
            } else {
                appendText("Nothing to analyse", textArea);
            }
        }
    }

    private class CorrectButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            correctCount++;
            JButton button = (JButton) e.getSource();
            button.setEnabled(false);
        }
    }

    private class IncorrectButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            incorrectCount++;
            JButton button = (JButton) e.getSource();
            button.setEnabled(false);
        }
    }

    /**
     * Listener for the open / file selection button.
     */
    private class OpenButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            File currentDirectory = new File(ResourceUtils.getResourcePathAsString(StringConstants.TEST));
            fileChooser.setCurrentDirectory(currentDirectory);

            int returnVal = fileChooser.showOpenDialog(ClassificationPanel.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                if (imageFiles != null && !imageFiles.isEmpty()) {
                    imageFiles.clear();
                    processedImagesArea.removeAll();
                }

                File file = fileChooser.getSelectedFile();
                imageFiles = FileWalker.discoverFilesOnPath(file.getPath());

                addThumbnailsToImageArea(imageFiles, true);
                appendText("Selected: " + file.getPath(), textArea);
            } else {
                appendText("Open command cancelled by user", textArea);
            }
        }
    }

    /**
     * Listener for the clear button.
     */
    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (imageFiles != null && !imageFiles.isEmpty()) {
                imageFiles.clear();
                selectedImagesArea.removeAll();
                processedImagesArea.removeAll();
                textArea.setText("");
                resultField.setText("");
                incorrectCount = 0;
                correctCount = 0;
                classified = false;
                addDummyThumbnails();
                repaintParent();
            } else {
                appendText("Nothing to clear", textArea);
            }
        }
    }

    /**
     * Listener for the toggle button.
     */
    private class ToggleButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
            histogramMode = abstractButton.getModel().isSelected();
            if (histogramMode) {
                appendText("Histogram mode enabled", textArea);
            } else {
                appendText("Histogram mode disabled", textArea);
            }

            if (classified) {
                // Redraw the processed image panel with current histogram mode.
                drawProcessedImagePanel();
            }
        }
    }

    /**
     * Generate an incorrect button.
     * @return  The button.
     */
    private JButton incorrectButton() {
        JButton incorrectButton = button("Incorrect", new IncorrectButtonListener());
        return incorrectButton;
    }

    /**
     * Generate a correct button.
     * @return  The button.
     */
    private JButton correctButton() {
        JButton correctButton = button("Correct   ", new CorrectButtonListener());
        return correctButton;
    }

    /**
     * Perform classification and draw the processed image panel.
     */
    private void drawProcessedImagePanel() {
        processedImagesArea.removeAll();
        // Create and add image strip per image file
        imageFiles.forEach(file -> {
            String classification = pipelineController.performClassification(file);
            processedImagesArea.add(createImageStrip(classification));
        });
        repaintParent();
    }

    /**
     * Create an image strip based on the current state of the
     * pipeline controller.
     * @param classification    The classification to add to this panel.
     * @return                  The panel.
     */
    private JPanel createImageStrip(String classification) {

        // Create panel
        JPanel processedImageStrip = new JPanel();
        GridLayout gridLayout = new GridLayout(1, 6);
        processedImageStrip.setLayout(gridLayout);
        processedImageStrip.setBorder(new EmptyBorder(10, 0, 0, 0));
        processedImageStrip.setAlignmentY(JPanel.TOP_ALIGNMENT);

        // Extract processed images
        BufferedImage original = pipelineController.getOriginal();
        BufferedImage preprocessed = pipelineController.getPreprocessed();
        BufferedImage segmented = pipelineController.getSegmented();
        BufferedImage postProcessed = pipelineController.getPostprocessed();

        // Check for histogram mode
        if (histogramMode) {
            original = ImageUtils.createGraphPlot(original);
            preprocessed = ImageUtils.createGraphPlot(preprocessed);
        }

        // Add images to the panel
        processedImageStrip.add(new JLabel(getImageIcon(original)));
        processedImageStrip.add(new JLabel(getImageIcon(preprocessed)));
        processedImageStrip.add(new JLabel(getImageIcon(segmented)));
        processedImageStrip.add(new JLabel(getImageIcon(postProcessed)));

        // Add feature extraction
        JLabel featureLabel = new JLabel(toHTMLString(pipelineController.getFeaturePayload()));
        featureLabel.setVerticalAlignment(JLabel.TOP);
        featureLabel.setHorizontalAlignment(JLabel.CENTER);
        processedImageStrip.add(featureLabel);

        // Add classification
        JLabel classificationLabel = new JLabel(toHTMLString(classification));
        classificationLabel.setVerticalAlignment(JLabel.TOP);
        classificationLabel.setHorizontalAlignment(JLabel.CENTER);
        processedImageStrip.add(classificationLabel);

        // Add correct / incorrect buttons
        JPanel analysisButtonsPanel = new JPanel();
        analysisButtonsPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
        BoxLayout boxLayout = new BoxLayout(analysisButtonsPanel, BoxLayout.Y_AXIS);
        analysisButtonsPanel.setLayout(boxLayout);
        analysisButtonsPanel.add(correctButton());
        analysisButtonsPanel.add(incorrectButton());
        processedImageStrip.add(analysisButtonsPanel);

        return processedImageStrip;
    }

    /**
     * Create a html string for a classification
     * @param classification    The classification string.
     * @return                  The HTML string.
     */
    private String toHTMLString(String classification) {
        return "<html><p></p><p><i><u>Classification</u></i></p><p></p>" +
                "<p><b>" + classification + "</b></p></html>";
    }

    /**
     * Create a html string for a feature payload.
     * @param payload   The payload.
     * @return          The HTML string.
     */
    private String toHTMLString(FeaturePayload payload) {
        return "<html><p></p><p><i><u>Feature Extraction</u></i></p><p></p>" +
                "<p>Area: " + payload.getArea() + "</p>" +
                "<p>Perimeter: " + payload.getPerimeter() + "</p>" +
                "<p>Compactness: " + payload.getCompactness() + "</p></html>";
    }

    /**
     * Convert an image to an image icon.
     * @param image The image.
     * @return      the image icon.
     */
    private ImageIcon getImageIcon(BufferedImage image) {
        ImageIcon icon = new ImageIcon(image);
        return resizeIcon(icon, processedImageSize, processedImageSize);
    }
}
