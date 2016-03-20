package ui;

import javax.swing.table.DefaultTableModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * An override of the DefaultTableModel that provides the column names and other specifics for the results view.
 */
public class ResultTableModel extends DefaultTableModel {

    private static String columnNames[] = { "Date", "Preprocessing", "Segmentation", "Post Processing", "Feature Extraction", "Classifier", "Score" };

    /**
     * Constructs a blank table model.
     */
    public ResultTableModel() {

        super(null, columnNames);
    }

    /**
     * Constructs the table model with the specified JSON data file.
     * @param jsonPath
     */
    public ResultTableModel(String jsonPath) {

        super(null, columnNames);

        Gson gson = new GsonBuilder().create();

        FileReader reader = null;

        try {
            reader = new FileReader(jsonPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader bufferedReader = new BufferedReader(reader);

        String[][] fileData = gson.fromJson(bufferedReader, String[][].class);

        for (String[] row: fileData) {

            this.addRow(row);

        }
    }

}
