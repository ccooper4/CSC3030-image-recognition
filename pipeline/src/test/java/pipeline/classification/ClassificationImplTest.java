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
import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ClassificationImplTest {
    public BufferedImage testingImage;
    public FeatureExtractionImpl featureExtraction;
    public ClassificationImpl classification;

    public FeaturePayload featurePayload;
    public final int area = 23523;
    public final int perimeter = 1740;
    public final int compactness = 128;
    public final int variance = 5329;
    public String expectedClassification = "APPLE";
    public List<FeaturePayload> payloads;

    @Before
    /**
     * Setup method to set features and create class
     */
    public void setup() {
        featureExtraction = new FeatureExtractionImpl();
        featurePayload = new FeaturePayload();
        featurePayload.setArea(area);
        featurePayload.setPerimeter(perimeter);
        featurePayload.setCompactness(compactness);
        featurePayload.setTextureVariance(variance);

        ConfigurationUtils.writeProperty(StringConstants.K_VALUE, "3");
        classification = new ClassificationImpl();
        buildPayload();

        for (FeaturePayload feature : payloads) {
            classification.train(featurePayload);
        }
    }

    /**
     * Method to test that the classification is producing correct results
     */
    @Test
    public void testPerformClassification() {
        assertEquals(expectedClassification, classification.classify(featurePayload));
    }

    /**
     * Build the set of payloads to compare the test image against
     */
    public void buildPayload()
    {
        FeaturePayload feature1 = new FeaturePayload();
        feature1.setArea(22747);
        feature1.setPerimeter(10655);
        feature1.setCompactness(4990);
        feature1.setTextureVariance(5041);
        feature1.setClassName("CAR");
        payloads.add(feature1);

        FeaturePayload feature2 = new FeaturePayload();
        feature2.setArea(29420);
        feature2.setPerimeter(9505);
        feature2.setCompactness(3070);
        feature2.setTextureVariance(4624);
        feature2.setClassName("CAR");
        payloads.add(feature2);

        FeaturePayload feature3 = new FeaturePayload();
        feature3.setArea(28358);
        feature3.setPerimeter(9592);
        feature3.setCompactness(3244);
        feature3.setTextureVariance(4624);
        feature3.setClassName("CAR");
        payloads.add(feature3);

        FeaturePayload feature4 = new FeaturePayload();
        feature4.setArea(28358);
        feature4.setPerimeter(9592);
        feature4.setCompactness(3244);
        feature4.setTextureVariance(4624);
        feature4.setClassName("CAR");
        payloads.add(feature4);

        FeaturePayload feature5 = new FeaturePayload();
        feature5.setArea(23444);
        feature5.setPerimeter(1599);
        feature5.setCompactness(109);
        feature5.setTextureVariance(5329);
        feature5.setClassName("APPLE");
        payloads.add(feature5);

        FeaturePayload feature6 = new FeaturePayload();
        feature6.setArea(22878);
        feature6.setPerimeter(1915);
        feature6.setCompactness(160);
        feature6.setTextureVariance(5476);
        feature6.setClassName("APPLE");
        payloads.add(feature6);

        FeaturePayload feature7 = new FeaturePayload();
        feature7.setArea(22372);
        feature7.setPerimeter(2146);
        feature7.setCompactness(205);
        feature7.setTextureVariance(5329);
        feature7.setClassName("APPLE");
        payloads.add(feature7);

        FeaturePayload feature8 = new FeaturePayload();
        feature8.setArea(23523);
        feature8.setPerimeter(1740);
        feature8.setCompactness(128);
        feature8.setTextureVariance(5329);
        feature8.setClassName("APPLE");
        payloads.add(feature8);
    }

}
