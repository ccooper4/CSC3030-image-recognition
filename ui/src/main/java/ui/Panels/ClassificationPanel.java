package ui.Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * The panel used for classification UI
 */
public class ClassificationPanel extends BasePanel{

    private JPanel processedImagesArea;
    private JScrollPane processedImagesAreaScrollPane;

    public ClassificationPanel() {
        super();

        openButton = button("Choose test image(s)", new OpenButtonListener());
        clearButton = button("Clear selection", new ClearButtonListener());
        actionButton = button("Classify", new ClassifyButtonListener());

        buttonPanel.add(openButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(actionButton);

        processedImagesArea = new JPanel(new FlowLayout());
        processedImagesAreaScrollPane = new JScrollPane(processedImagesArea);
        processedImagesAreaScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        processedImagesAreaScrollPane.setVisible(false);

        JPanel middleSection = new JPanel(new BorderLayout());
        middleSection.add(textAreaScrollPane, BorderLayout.NORTH);
        middleSection.add(processedImagesAreaScrollPane, BorderLayout.SOUTH);

        add(buttonPanel, BorderLayout.NORTH);
        add(middleSection, BorderLayout.CENTER);
        add(selectedImagesAreaScrollPane, BorderLayout.SOUTH);
    }

    private class ClassifyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (imageFiles != null && !imageFiles.isEmpty()) {
                Map<File, String> fileDirectoryMap = new HashMap<>();
                imageFiles.forEach(file -> {
                    String directory = file.getParentFile().getName();
                    appendText("Processing - \t \t File: " + file.getName() + "\t \t Type: " + directory.toUpperCase(), textArea);
                });
            } else {
                appendText("No test images selected", textArea);
            }
        }
    }


}
