package pipeline;

/**
 * Provides a description of the various stages of the current pipeline.
 */
public class PipelineDescription {

    private String preProcessingDescription;
    private String segmentationDescription;
    private String postProcessingDescription;
    private String featureExtractionDescription;
    private String classifierDescription;

    /**
     * Gets the feature extraction description.
     * @return
     */
    public String getFeatureExtractionDescription() {
        return featureExtractionDescription;
    }

    /**
     * Sets the feature extraction description.
     * @param featureExtractionDescription The new description.
     */
    public void setFeatureExtractionDescription(String featureExtractionDescription) {
        this.featureExtractionDescription = featureExtractionDescription;
    }

    /**
     * Gets the classifier description.
     * @return
     */
    public String getClassifierDescription() {
        return classifierDescription;
    }

    /**
     * Sets the classifier description
     * @param classifierDescription The new description.
     */
    public void setClassifierDescription(String classifierDescription) {
        this.classifierDescription = classifierDescription;
    }

    /**
     * Gets the pre proc description.
     * @return
     */
    public String getPreProcessingDescription() {
        return preProcessingDescription;
    }

    /**
     * Sets the pre proc description.
     * @param preProcessingDescription The new description.
     */
    public void setPreProcessingDescription(String preProcessingDescription) {
        this.preProcessingDescription = preProcessingDescription;
    }

    /**
     * Gets the segmentation description.
     * @return
     */
    public String getSegmentationDescription() {
        return segmentationDescription;
    }

    /**
     * Sets the segmentation description.
     * @param segmentationDescription The new description.
     */
    public void setSegmentationDescription(String segmentationDescription) {
        this.segmentationDescription = segmentationDescription;
    }

    /**
     * Gets the post processing description.
     * @return
     */
    public String getPostProcessingDescription() {
        return postProcessingDescription;
    }

    /**
     * Sets the post processing description.
     * @param postProcessingDescription The new description.
     */
    public void setPostProcessingDescription(String postProcessingDescription) {
        this.postProcessingDescription = postProcessingDescription;
    }
}
