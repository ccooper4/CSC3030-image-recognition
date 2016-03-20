package ui.Panels;

import pipeline.featureextraction.FeaturePayload;
import ui.ResultTableModel;
import util.FileWalker;
import util.ResourceUtils;
import util.StringConstants;
import util.image.ImageUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

/**
 * The panel used for the report UI
 */
public class ResultPanel extends BasePanel {

    /**
     * Constrcuts a new result panel with the specified model.
     * @param model The table model.
     */
    public ResultPanel(ResultTableModel model) {
        super();

        JTable table = new JTable(model);

        JScrollPane pane = new JScrollPane(table);

        this.add(pane,BorderLayout.CENTER);
    }
}
