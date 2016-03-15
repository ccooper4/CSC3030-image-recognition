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

    /**
     * Classify the input image FeaturePayload
     * @param testPayload
     * @return
     */
    public String classify(FeaturePayload testPayload) {
        this.inputFeature = testPayload;
        String result = rankTestSet();

        if (result == null) {
            return "UNTRAINED";
        }
        return result;
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
     * @return The result classification string
     */
    private String rankTestSet(){
        List<Neighbour> neighbours = new ArrayList<>();

        for (FeaturePayload payload  : trainingSet) {
            double difference = 0;

            if(payload.getCompactness() > inputFeature.getCompactness()){
                difference = payload.getCompactness() - inputFeature.getCompactness();
            }
            else
                difference =  inputFeature.getCompactness() - payload.getCompactness();

            Neighbour tempNeighbour = new Neighbour();
            tempNeighbour.setClassName(payload.getClassName());
            tempNeighbour.setDifference(difference);
            neighbours.add(tempNeighbour);
        }

        neighbours.sort((p1, p2) -> p1.getDifference().compareTo(p2.getDifference()));
        return calculateFrequency(neighbours);
    }

    /**
     * Calculate the frequency of the neighbours and return the most frequent
     * @param neighbours
     * @return The class name
     */
    public String calculateFrequency(List<Neighbour> neighbours){
        Map<String, Integer> frequencyMap = new HashMap<>();

        for(int i= 0; i < k; i++){
            if(frequencyMap.containsKey(neighbours.get(i).getClassName())){
                frequencyMap.put(neighbours.get(i).getClassName(), frequencyMap.get(neighbours.get(i).getClassName()) + 1);
            }
            else
                frequencyMap.put(neighbours.get(i).getClassName(), 1);
        }

        Map.Entry<String, Integer> max = null;

        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet())
        {
            if (max == null || entry.getValue().compareTo(max.getValue()) > 0)
            {
                max = entry;
            }
        }
        return max.getKey();
    }
}
