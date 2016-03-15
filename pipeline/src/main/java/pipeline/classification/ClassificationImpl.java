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

    private static List<FeaturePayload> trainingSet;

    /**
     * Default constructor
     */
    public ClassificationImpl() {
        this.k = Integer.parseInt(ConfigurationUtils.loadProperty(StringConstants.K_VALUE));
    }

    public void train(FeaturePayload trainingPayload) {
        if (trainingSet == null) {
            trainingSet = new ArrayList<>();
        }
        trainingSet.add(trainingPayload);
    }

    public String classify(FeaturePayload testPayload) {
        if (trainingSet == null) {
            return "UNTRAINED";
        }
        return "TEST CLASS";
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
