package ui;

import utils.ResourceUtils;
import utils.StringConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * The panel used for training ui
 */
public class TrainingPanel extends JPanel {
    static private final String newline = "\n";
    private JFileChooser fileChooser;
    private JButton openButton;
    private JTextArea textArea;

    public TrainingPanel() {
        super(new BorderLayout());
        fileChooser = createFileChooser();
        openButton = createOpenButton();

        textArea = new JTextArea(5,20);
        textArea.setMargin(new Insets(5,5,5,5));
        textArea.setEditable(false);
        JScrollPane textAreaScrollPane = new JScrollPane(textArea);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);

        add(buttonPanel, BorderLayout.PAGE_START);
        add(textAreaScrollPane, BorderLayout.CENTER);
    }

    private JFileChooser createFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        File baseDirectory = new File(ResourceUtils.getResourcePathAsString(StringConstants.ASSIGNMENT));
        fileChooser.setCurrentDirectory(baseDirectory);
        return fileChooser;
    }

    private JButton createOpenButton() {
        JButton button = new JButton("Choose training images directory");
        button.addActionListener(new OpenButtonListener());
        return button;
    }

    public class OpenButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int returnVal = fileChooser.showOpenDialog(TrainingPanel.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                //This is where a real application would open the file.
                textArea.append("Opening: " + file.getName() + "." + newline);
            } else {
                textArea.append("Open command cancelled by user." + newline);
            }
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }
}
