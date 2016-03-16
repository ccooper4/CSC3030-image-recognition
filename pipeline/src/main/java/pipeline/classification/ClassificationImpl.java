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
     * The stored training set
     */
    private static Set<FeaturePayload> trainingSet;

    /**
     * The k value to use for nearest neighbour
     */
    private int k;

    /**
     * Default constructor
     */
    public ClassificationImpl() {
        this.k = Integer.parseInt(ConfigurationUtils.loadProperty(StringConstants.K_VALUE));
    }

    /**
     * Train the classifier with a preset feature payload.
     * @param trainingPayload   The training feature payload.
     */
    public void train(FeaturePayload trainingPayload) {
        if (trainingSet == null) {
            trainingSet = new HashSet<>();
        }
        trainingSet.add(trainingPayload);
    }

    public void clearTraining() {
        if (trainingSet != null && !trainingSet.isEmpty()) {
            trainingSet.clear();
        }
    }

    /**
     * Classify the input image FeaturePayload.
     * @param testPayload   The payload to classify.
     * @return              The class of the payload.
     */
    public String classify(FeaturePayload testPayload) {

        if (trainingSet == null || trainingSet.isEmpty()) {
            return "UNTRAINED";
        }

        return classifyPayload(testPayload);
    }

    /**
     * Order the test set in order of closeness and get the resultant class name.
     * @return The result classification string
     */
    private String classifyPayload(FeaturePayload testPayload){
        List<Neighbour> neighbours = new ArrayList<>();

        for (FeaturePayload payload  : trainingSet) {
            double difference;

            if (payload.getCompactness() > testPayload.getCompactness()) {
                difference = payload.getCompactness() - testPayload.getCompactness();
            }
            else {
                difference =  testPayload.getCompactness() - payload.getCompactness();
            }

            Neighbour tempNeighbour = new Neighbour();
            tempNeighbour.setClassName(payload.getClassName());
            tempNeighbour.setDifference(difference);
            neighbours.add(tempNeighbour);
        }

        return calculateFrequency(neighbours);
    }

    /**
     * Calculate the frequency of the neighbours and return the most frequent
     * @param neighbours    The neighbours
     * @return              The class name
     */
    private String calculateFrequency(List<Neighbour> neighbours){

        neighbours.sort((p1, p2) -> p1.getDifference().compareTo(p2.getDifference()));

        Map<String, Integer> frequencyMap = new HashMap<>();

        for (int i= 0; i < k; i++) {
            if (frequencyMap.containsKey(neighbours.get(i).getClassName())) {
                frequencyMap.put(neighbours.get(i).getClassName(), frequencyMap.get(neighbours.get(i).getClassName()) + 1);
            } else {
                frequencyMap.put(neighbours.get(i).getClassName(), 1);
            }
        }

        Map.Entry<String, Integer> max = null;

        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            if (max == null || entry.getValue().compareTo(max.getValue()) > 0) {
                max = entry;
            }
        }
        return max.getKey();
    }

    /**
     * Inner class used to wrap the differences between compactness in the training set
     * and the feature payload under classification.
     */
    private class Neighbour {
        /**
         * The difference of compactness
         */
        private Double difference;

        /**
         * The Class name
         */
        private String className;

        private Double getDifference() {
            return difference;
        }

        private void setDifference(Double difference) {
            this.difference = difference;
        }

        private String getClassName() {
            return className;
        }

        private void setClassName(String className) {
            this.className = className;
        }
    }
}
