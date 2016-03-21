package ui.Panels;

import ui.ResultTableModel;
import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
/**
 * The panel used for the report UI
 */
public class ResultPanel extends JPanel {

    /**
     * Constrcuts a new result panel with the specified model.
     * @param model The table model.
     */
    public ResultPanel(ResultTableModel model) {
        super(new BorderLayout());

        JTable table = new JTable(model);
        table.setRowHeight(75);

        JScrollPane pane = new JScrollPane(table);

        this.add(pane,BorderLayout.CENTER);
    }
}
