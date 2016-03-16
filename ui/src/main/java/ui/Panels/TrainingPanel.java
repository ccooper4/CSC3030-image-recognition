package ui.Panels;

import util.FileWalker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * The panel used for training ui
 */
public class TrainingPanel extends BasePanel {

    public TrainingPanel() {
        super();

        openButton = button("Choose training image(s)", new OpenButtonListener());
        clearButton = button("Reset Training", new ClearButtonListener());
        actionButton = button("Train System", new TrainButtonListener());

        buttonPanel.add(openButton);
        buttonPanel.add(actionButton);
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.NORTH);
        add(textAreaScrollPane, BorderLayout.CENTER);
        add(selectedImagesAreaScrollPane, BorderLayout.SOUTH);
    }

    private class TrainButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (imageFiles != null && !imageFiles.isEmpty()) {
                appendText(LINE_BREAK, textArea);
                appendText("Beginning training", textArea);
                appendText(LINE_BREAK, textArea);
                imageFiles.forEach(file -> appendText("Processing - \t \t File: " + file.getName() + "\t \t Known Type: "
                        + file.getParentFile().getName().toUpperCase(), textArea));

                pipelineController.performTraining(imageFiles);

                appendText(LINE_BREAK, textArea);
                appendText("Training Complete", textArea);
                appendText(LINE_BREAK, textArea);
            } else {
                appendText("No training images selected", textArea);
            }
        }
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
                textArea.setText("");
                pipelineController.clearTraining();
                addDummyThumbnails();
                repaintParent();
            } else {
                appendText("Nothing to clear", textArea);
            }
        }
    }

    private class OpenButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int returnVal = fileChooser.showOpenDialog(TrainingPanel.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                imageFiles = FileWalker.discoverFilesOnPath(file.getPath());
                addThumbnailsToImageArea(imageFiles, true);
                appendText("Selected: " + file.getPath(), textArea);
            } else {
                appendText("Open command cancelled by user", textArea);
            }
        }
    }
}
