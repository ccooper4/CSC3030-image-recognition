package pipeline.classification;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pipeline.featureextraction.FeatureExtractionImpl;
import pipeline.featureextraction.FeaturePayload;
import util.ConfigurationUtils;
import util.ResourceUtils;
import util.StringConstants;
import util.image.ImageUtils;

import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;

/**
 * Created by Chris on 23/03/2016.
 */
public class ClassificationImplTest {
    public BufferedImage testingImage;
    public FeatureExtractionImpl featureExtraction;
    public ClassificationImpl classification;

    public FeaturePayload featurePayload;
    public final int area = 12605;
    public final int perimeter = 1075;
    public final int compactness = 91;
    public String expectedClassification = "Apple";

    @Before
    public void loadTestingImage() {
        testingImage = ImageUtils.readInImage(ResourceUtils.getResourcePathAsString("segmented.png"));
        featureExtraction = new FeatureExtractionImpl();
        featurePayload = new FeaturePayload();
        featurePayload.setArea(area);
        featurePayload.setPerimeter(perimeter);
        featurePayload.setCompactness(compactness);
        ConfigurationUtils.writeProperty(StringConstants.K_VALUE, "3");
        classification = new ClassificationImpl();
    }

    @Test
    public void testPerformClassification() {
        //assertEquals(expectedClassification, classification.classify(featurePayload));
    }
}
