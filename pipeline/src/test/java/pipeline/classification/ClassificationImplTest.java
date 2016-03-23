package pipeline.classification;

import org.junit.Before;
import org.junit.Test;
import pipeline.featureextraction.FeatureExtractionImpl;
import pipeline.featureextraction.FeaturePayload;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ClassificationImplTest {
    private FeatureExtractionImpl featureExtraction;
    private ClassificationImpl classification;

    private FeaturePayload featurePayload;
    private final int area = 23523;
    private final int perimeter = 1740;
    private final int variance = 5329;
    private String expectedClassification = "APPLE";
    private List<FeaturePayload> payloads;

    @Before
    /**
     * Setup method to set features and create class
     */
    public void setup() {
        payloads = new ArrayList<>();
        featureExtraction = new FeatureExtractionImpl();
        featurePayload = new FeaturePayload(area, perimeter, variance);

        classification = new ClassificationImpl();
        buildPayload();

        for (FeaturePayload feature : payloads) {
            classification.train(feature);
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
        FeaturePayload feature1 = new FeaturePayload(22747, 10655, 5041);
        feature1.setClassName("CAR");
        payloads.add(feature1);

        FeaturePayload feature2 = new FeaturePayload(29420, 9505, 4624);
        feature2.setClassName("CAR");
        payloads.add(feature2);

        FeaturePayload feature3 = new FeaturePayload(28358, 9592, 4624);
        feature3.setClassName("CAR");
        payloads.add(feature3);

        FeaturePayload feature4 = new FeaturePayload(28358, 9592, 4624);
        feature4.setClassName("CAR");
        payloads.add(feature4);

        FeaturePayload feature5 = new FeaturePayload(23444, 1599, 5329);
        feature5.setClassName("APPLE");
        payloads.add(feature5);

        FeaturePayload feature6 = new FeaturePayload(22878, 1915, 5476);
        feature6.setClassName("APPLE");
        payloads.add(feature6);

        FeaturePayload feature7 = new FeaturePayload(22372, 2146, 5329);
        feature7.setClassName("APPLE");
        payloads.add(feature7);

        FeaturePayload feature8 = new FeaturePayload(23523, 1740, 5329);
        feature8.setClassName("APPLE");
        payloads.add(feature8);
    }
}
