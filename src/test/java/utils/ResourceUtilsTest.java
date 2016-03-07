package utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class ResourceUtilsTest {

    @Test
    public void testGetResourcePath() {
        String path = ResourceUtils.getResourcePathAsString("boat256.jpg");
        assertNotNull(path);
    }
}