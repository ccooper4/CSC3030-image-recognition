package utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigurationUtilsTest {

    String propertyName = "testName";
    String propertyValue = "testValue";

    @Before
    public void writeProperty() {
        ConfigurationUtils.writeProperty(propertyName, propertyValue);
    }

    @After
    public void removeProperty() {
        ConfigurationUtils.removeProperty(propertyName);
    }

    @Test
    public void testLoadProperty() {
        String result = ConfigurationUtils.loadProperty(propertyName);
        assertEquals(propertyValue, result);
    }

}
