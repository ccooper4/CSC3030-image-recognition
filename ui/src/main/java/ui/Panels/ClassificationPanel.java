package ui.Panels;

import pipeline.featureextraction.FeaturePayload;
import qub.visionsystem.HistogramException;
import util.image.ImageUtils;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * The panel used for classification UI
 */
public class ClassificationPanel extends BasePanel {

    private static final int imageSize = 120;
    private static final int processedImageSize = 180;
    private JPanel processedImagesArea;
    private JScrollPane processedImagesAreaScrollPane;

    public ClassificationPanel() {
        super(imageSize);

        openButton = button("Choose test image(s)", new OpenButtonListener());
        clearButton = button("Clear", new ClearButtonListener());
        actionButton = button("Classify", new ClassifyButtonListener());

        buttonPanel.add(openButton);
        buttonPanel.add(actionButton);
        buttonPanel.add(clearButton);

        processedImagesArea = new JPanel();
        processedImagesArea.setLayout(new BoxLayout(processedImagesArea, BoxLayout.Y_AXIS));
        processedImagesAreaScrollPane = new JScrollPane(processedImagesArea);

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
                imageFiles.forEach(file -> {

                    appendText("Processing - \t \t File: " + file.getName(), textArea);

                    try {
                        pipelineController.processAnImage(ImageUtils.readInImage(file.getPath()));
                    } catch (HistogramException ex) {
                        log.error("HistogramException", ex);
                    }

                    JPanel processedImageStrip = new JPanel();
                    GridLayout gridLayout = new GridLayout(1, 6);
                    processedImageStrip.setLayout(gridLayout);
                    processedImageStrip.setBorder(new EmptyBorder(10, 0, 0, 0));
                    processedImageStrip.setAlignmentY(JPanel.TOP_ALIGNMENT);

                    processedImageStrip.add(new JLabel(getImageIcon(pipelineController.getOriginal())));
                    processedImageStrip.add(new JLabel(getImageIcon(pipelineController.getPreprocessed())));
                    processedImageStrip.add(new JLabel(getImageIcon(pipelineController.getSegmented())));
                    processedImageStrip.add(new JLabel(getImageIcon(pipelineController.getPostprocessed())));

                    JLabel featureLabel = new JLabel(toHTMLString(pipelineController.getFeaturePayload()));
                    featureLabel.setVerticalAlignment(JLabel.TOP);
                    featureLabel.setHorizontalAlignment(JLabel.CENTER);
                    processedImageStrip.add(featureLabel);

                    JLabel classificationLabel = new JLabel(toHTMLString("Example Class"));
                    classificationLabel.setVerticalAlignment(JLabel.TOP);
                    classificationLabel.setHorizontalAlignment(JLabel.CENTER);
                    processedImageStrip.add(classificationLabel);

                    processedImagesArea.add(processedImageStrip);
                });
                repaintParent();
            } else {
                appendText("No test images selected", textArea);
            }
        }
    }

    private String toHTMLString(String classification) {
        return "<html><p></p><p><i><u>Classification</u></i></p><p></p>" +
                "<p>" + classification + "</p></html>";
    }

    private String toHTMLString(FeaturePayload payload) {
        return "<html><p></p><p><i><u>Feature Extraction</u></i></p><p></p>" +
                "<p>Area: " + payload.getArea() + "</p>" +
                "<p>Perimeter: " + payload.getPerimeter() + "</p>" +
                "<p>Compactness: " + payload.getCompactness() + "</p></html>";
    }

    private ImageIcon getImageIcon(BufferedImage image) {
        ImageIcon icon = new ImageIcon(image);
        return resizeIcon(icon, processedImageSize, processedImageSize);
    }

    /**
     * Clear the previously chosen image files.
     */
    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (imageFiles != null && !imageFiles.isEmpty()) {
                imageFiles.clear();
                selectedImagesArea.removeAll();
                processedImagesArea.removeAll();
                appendText("Cleared selection", textArea);
                addDummyThumbnails();
                repaintParent();
            } else {
                appendText("Nothing to clear", textArea);
            }
        }
    }
}
