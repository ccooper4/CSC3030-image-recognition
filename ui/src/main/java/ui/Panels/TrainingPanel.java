package ui.Panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * The panel used for training ui
 */
public class TrainingPanel extends BasePanel {

    public TrainingPanel() {
        super();

        openButton = button("Choose training image(s)", new OpenButtonListener());
        clearButton = button("Clear Selection", new ClearButtonListener());
        actionButton = button("Train System", new TrainButtonListener());

        buttonPanel.add(openButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(actionButton);

        add(buttonPanel, BorderLayout.NORTH);
        add(textAreaScrollPane, BorderLayout.CENTER);
        add(selectedImagesAreaScrollPane, BorderLayout.SOUTH);
    }

    private class TrainButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (imageFiles != null && !imageFiles.isEmpty()) {
                Map<File, String> fileDirectoryMap = new HashMap<>();
                appendText(LINE_BREAK, textArea);
                appendText("Beginning training", textArea);
                appendText(LINE_BREAK, textArea);
                imageFiles.forEach(file -> {
                    String directory = file.getParentFile().getName();
                    fileDirectoryMap.put(file, directory);
//                    BufferedImage image = ImageUtils.readInImage(file.getPath());
//                    pipelineController.processAnImage(image);
                    appendText("Processing - \t \t File: " + file.getName() + "\t \t Type: "
                                                + directory.toUpperCase(), textArea);
                });
                appendText(LINE_BREAK, textArea);
                appendText("Training Complete", textArea);
                appendText(LINE_BREAK, textArea);
            } else {
                appendText("No training images selected", textArea);
            }
        }
    }
}
