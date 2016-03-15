package pipeline.classification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pipeline.featureextraction.FeaturePayload;
import util.ConfigurationUtils;
import util.StringConstants;
import java.util.*;

public class ClassificationImpl implements IClassification {
    /**
     * The logger for this class.
     */
    private static final Logger log = LoggerFactory.getLogger(ClassificationImpl.class);

    /**
     * Constructs a new instance of the ClassificationImpl pipeline block.
     * @param testSet
     * @param testingImage
     */
    public ClassificationImpl(Map<FeaturePayload, String> testSet, FeaturePayload testingImage) {
        this.k = Integer.parseInt(ConfigurationUtils.loadProperty(StringConstants.K_VALUE));
        this.inputFeature = testingImage;
        this.testSet = testSet;
    }

    /**
     * The k value to use for nearest neighbour
     */
    private int k;

    /**
     * The input feature payload
     */
    private Map<FeaturePayload, String> testSet;

    /**
     * The input feature payload
     */
    private FeaturePayload inputFeature;

    /**
     * Order the testset in order of closeness
     * @return The ranked set from start to 'k'
     */
    private List<Neighbour> rankTestSet(){
        List<Neighbour> neighbours = new ArrayList<>();

        for (Map.Entry<FeaturePayload, String> entry : testSet.entrySet()) {
            double difference = 0;

            if(entry.getKey().getCompactness() > inputFeature.getCompactness())
            {
                difference = entry.getKey().getCompactness() - inputFeature.getCompactness();
            }

            else
            {
                difference =  inputFeature.getCompactness() - entry.getKey().getCompactness();
            }

            Neighbour tempNeighbour = new Neighbour();
            tempNeighbour.setClassName(entry.getValue());
            tempNeighbour.setDifference(difference);
        }
        neighbours.sort((p1, p2) -> p1.getDifference().compareTo(p2.getDifference()));

        return neighbours.subList(0, k-1);
    }
}
