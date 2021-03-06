package ui;

import javax.swing.table.DefaultTableModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConfigurationUtils;
import util.StringConstants;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * An override of the DefaultTableModel that provides the column names and other specifics for the results view.
 */
public class ResultTableModel extends DefaultTableModel {
    private static Logger log = LoggerFactory.getLogger(ResultTableModel.class);

    private static String columnNames[] = { "Date", "Preprocessing", "Segmentation", "Post Processing", "Feature Extraction", "Classifier", "Score" };

    private String jsonPath = ConfigurationUtils.loadProperty(StringConstants.RESULT_FILE);

    private Gson jsonBuilder;

    /**
     * Constructs the table model with the specified JSON data file.
     */
    public ResultTableModel() {

        super(null, columnNames);

        jsonBuilder = new GsonBuilder().create();

        FileReader reader = null;

        try {
            reader = new FileReader(jsonPath);
        } catch (FileNotFoundException e) {
            log.error("File not found exception", e);
            return;
        }
        catch (Exception e) {
            log.error("Exception", e);
        }

        String[][] fileData = reader == null ? null : jsonBuilder.fromJson(reader, String[][].class);

        if (fileData != null) {

            //Sort by the date - need to allow for non-linear insert following merge.
            Arrays.sort(fileData, (a, b) -> {
                final String timeA = a[0];
                final String timeB = b[0];
                return timeA.compareTo(timeB);
            });


            for (String[] row : fileData) {
                super.addRow(row);
            }
        }
    }

    /**
     * Adds a new row to the table.
     * @param rowData
     */
    @Override
    public void addRow(Object[] rowData) {

        super.addRow(rowData);

        String[][] jsonData = new String[this.getRowCount()][columnNames.length];

        //Get each vector in the data vector
        for (int row = 0; row < this.getRowCount(); row++)
        {
            for (int col = 0; col < columnNames.length; col++)
            {
                jsonData[row][col] = this.getValueAt(row,col).toString();
            }
        }

        String json = jsonBuilder.toJson(jsonData);

        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(jsonPath), StandardCharsets.UTF_8);

            writer.write(json);

            writer.flush();
            writer.close();

        } catch (IOException e) {
            log.error("IOException", e);
        }

    }

}
