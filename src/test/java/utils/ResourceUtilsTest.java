package utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class ResourceUtilsTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetResourcePath() {
        String path = ResourceUtils.getResourcePath("boat256.jpg");
        assertNotNull(path);
    }
}