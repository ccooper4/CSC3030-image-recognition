package ui.Panels;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pipeline.controller.IPipelineController;
import pipeline.controller.PipelineController;
import util.ResourceUtils;
import util.StringConstants;
import javax.swing.*;
import java.awt.*;;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class BasePanel extends JPanel {
    protected static final int DEFAULT_IMAGE_SIZE = 180;
    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final int IMAGE_SIZE;
    protected final String LINE_BREAK = "-------------------------------";
    protected final String NEW_LINE = "\n";

    protected JFileChooser fileChooser;

    protected JButton openButton;
    protected JButton clearButton;
    protected JButton actionButton;

    protected JTextArea textArea;
    protected JPanel selectedImagesArea;

    protected JScrollPane selectedImagesAreaScrollPane;
    protected JScrollPane textAreaScrollPane;

    protected JPanel buttonPanel;

    protected List<File> imageFiles;
    protected IPipelineController pipelineController;

    public BasePanel() {
        this(DEFAULT_IMAGE_SIZE);
    }

    public BasePanel(int imageSize) {
        super(new BorderLayout());
        IMAGE_SIZE = imageSize;

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
        addDummyThumbnails();

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
     * Append text to the text area.
     * @param text      The text to append.
     * @param textArea  Thje text area.
     */
    protected void appendText(String text, JTextArea textArea) {
        textArea.append(text + NEW_LINE);
    }

    /**
     * Repaint the parent of the training panel.
     */
    protected void repaintParent() {
        this.getParent().repaint();
    }

    /**
     * Add a number of dummy thumbnails to the image area to prepopulate it's size.
     */
    protected void addDummyThumbnails() {
        List<File> files = new ArrayList<>();
        for (int i =0; i < 40; i++) {
            files.add(new File(ResourceUtils.getResourcePathAsString("images/grey_square.png")));
        }
        addThumbnailsToImageArea(files, false);
    }

    protected ImageIcon getImageIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        return resizeIcon(icon, width, height);
    }

    protected ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    /**
     * Add thumbnails to the image area for the selected image files.
     * @param files The imageFiles.
     */
    protected void addThumbnailsToImageArea(java.util.List<File> files, boolean repaintParent) {
        selectedImagesArea.removeAll();
        for (File file : files) {
            ImageIcon icon = getImageIcon(file.getPath(), IMAGE_SIZE, IMAGE_SIZE);
            JLabel picture = new JLabel(icon);
            selectedImagesArea.add(picture);
            selectedImagesAreaScrollPane.setVisible(true);
        }

        if (repaintParent) {
            repaintParent();
        }
    }
}
