package pipeline.classification;

import pipeline.featureextraction.FeaturePayload;

/**
 * Created by ccoop on 14/03/2016.
 */
public class Neighbour {
    /**
     * The difference of compactness
     */
    private Double difference;

    /**
     * The Class name
     */
    private String className;

    public Double getDifference() {
        return difference;
    }

    public void setDifference(Double difference) {
        this.difference = difference;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
