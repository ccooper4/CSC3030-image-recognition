package pipeline.featureextraction;

import org.junit.Before;
import org.junit.Test;
import util.ResourceUtils;
import util.image.ImageUtils;

import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the feature extraction class.
 */
public class FeatureExtractionImplTest {

    private BufferedImage testingImage;
    private FeatureExtractionImpl featureExtraction;

    private FeaturePayload expectedFeaturePayload;
    private final int expectedArea = 12605;
    private final int expectedPerimeter = 1897;
    private final int expectedVariance = 10000;

    @Before
    public void loadTestingImage() {
        testingImage = ImageUtils.readInImage(ResourceUtils.getResourcePathAsString("segmented.png"));
        featureExtraction = new FeatureExtractionImpl();
        expectedFeaturePayload = new FeaturePayload(expectedArea, expectedPerimeter, expectedVariance);
    }

    @Test
    public void testPerformFeatureExtraction() {
        FeaturePayload actualFeaturePayload = featureExtraction.performFeatureExtraction(testingImage, testingImage);
        assertEquals(expectedFeaturePayload, actualFeaturePayload);
    }
}