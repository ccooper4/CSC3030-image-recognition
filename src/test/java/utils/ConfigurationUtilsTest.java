package utils;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigurationUtilsTest {

    @Test @Ignore
    public void testLoadProperty() {
        String propertyName = "testprop";
        String expectedValue = "testproperty";
        String result = ConfigurationUtils.loadProperty(propertyName);
        assertEquals(expectedValue, result);
    }

    @Test @Ignore
    public void testWriteProperty() {
        String propertyName = "testName";
        String propertyValue = "testValue";
        ConfigurationUtils.writeProperty(propertyName, propertyValue);
        String result = ConfigurationUtils.loadProperty(propertyName);
        assertEquals(propertyValue, result);
    }
}
