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

    public BufferedImage testingImage;
    public FeatureExtractionImpl featureExtraction;

    public FeaturePayload expectedFeaturePayload;
    public final int expectedArea = 12605;
    public final int expectedPerimeter = 1075;
    public final int expectedCompactness = 91;
    public final int expectedVariance = 10000;

    @Before
    public void loadTestingImage() {
        testingImage = ImageUtils.readInImage(ResourceUtils.getResourcePathAsString("segmented.png"));
        featureExtraction = new FeatureExtractionImpl();
        expectedFeaturePayload = new FeaturePayload();
        expectedFeaturePayload.setArea(expectedArea);
        expectedFeaturePayload.setPerimeter(expectedPerimeter);
        expectedFeaturePayload.setCompactness(expectedCompactness);
        expectedFeaturePayload.setTextureVariance(expectedVariance);
    }

    @Test
    public void testPerformFeatureExtraction() {
        FeaturePayload actualFeaturePayload = featureExtraction.performFeatureExtraction(testingImage, testingImage);
        assertEquals(expectedFeaturePayload, actualFeaturePayload);
    }
}