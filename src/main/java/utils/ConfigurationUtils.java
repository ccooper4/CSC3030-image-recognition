package utils;

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
        Properties properties = new Properties();
        OutputStream output = null;

        try {
            output = new FileOutputStream(ResourceUtils.getResourcePath("config.properties"));
            properties.setProperty(propertyName, propertyValue);
            properties.store(output, null);
        } catch (IOException e) {
            log.error("IOException", e);
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    log.error("IOException", e);
                }
            }
        }
    }
}