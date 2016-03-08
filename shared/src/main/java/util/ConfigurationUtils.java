package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class ConfigurationUtils {
    private static final Logger log = LoggerFactory.getLogger(ConfigurationUtils.class);
    private static final String PROP_FILE="config.properties";

    /**
     * Read a property given the property name
     * @param propertyName  The property name
     * @return              The property value
     */
    public static String loadProperty(String propertyName) {
        Properties properties = new Properties();
        InputStream input = null;
        String propertyValue = null;

        try {
            input = ConfigurationUtils.class.getClassLoader().getResourceAsStream(PROP_FILE);
            properties.load(input);
            propertyValue = properties.getProperty(propertyName);
        } catch (IOException e) {
            log.error("IOException", e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    log.error("IOException", e);
                }
            }
        }
        return propertyValue;
    }

    /**
     * Write a new property to the properties file
     * @param propertyName  The name of the property
     * @param propertyValue The value of the property
     */
    public static void writeProperty(String propertyName, String propertyValue) {
        writeProperty(propertyName, propertyValue, null);
    }

    /**
     * Write a new property to the properties file
     * @param propertyName  The name of the property
     * @param propertyValue The value of the property
     * @param comment       The comment to add to the property file
     */
    public static void writeProperty(String propertyName, String propertyValue, String comment) {
        File configFile = new File(ResourceUtils.getResourcePathAsString(PROP_FILE));
        try {
            Properties props = new Properties();
            props.setProperty(propertyName, propertyValue);
            FileWriter writer = new FileWriter(configFile, true);   // true = append to file
            writer.write("\n"); // new line before adding property
            props.store(writer, comment);
            writer.close();
        } catch (IOException e) {
            log.error("IOException", e);
        }
    }

    /**
     * Remove a property from the properties file
     * @param propertyName  The property name
     */
    public static void removeProperty(String propertyName) {
        try {
            File myFile = new File(ResourceUtils.getResourcePathAsString(PROP_FILE));
            Properties properties = new Properties();
            properties.load(new FileInputStream(myFile));
            properties.remove(propertyName);
            OutputStream out = new FileOutputStream(myFile);
            properties.store(out, null);
        } catch (IOException e) {
            log.error("IOException", e);
        }
    }
}