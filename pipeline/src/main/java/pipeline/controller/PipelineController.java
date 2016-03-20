package pipeline.controller;

import pipeline.PipelineDescription;
import pipeline.classification.ClassificationImpl;
import pipeline.classification.IClassification;
import pipeline.featureextraction.FeatureExtractionImpl;
import pipeline.featureextraction.FeaturePayload;
import pipeline.featureextraction.IFeatureExtraction;
import pipeline.postprocessing.IPostprocessing;
import pipeline.postprocessing.PostprocessingImpl;
import pipeline.preprocessing.IPreprocessing;
import pipeline.preprocessing.PreProcessingImpl;
import pipeline.segmentation.ISegmentation;
import pipeline.segmentation.SegmentationImpl;
import util.image.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * Controls the various stages of the pipeline.
 */
public class PipelineController implements IPipelineController {

    private IPreprocessing preprocessing;
    private ISegmentation segmentation;
    private IPostprocessing postprocessing;
    private IFeatureExtraction featureExtraction;
    private IClassification classification;

    private BufferedImage original;
    private BufferedImage preprocessed;
    private BufferedImage segmented;
    private BufferedImage postprocessed;
    private FeaturePayload featurePayload;

    public PipelineController() {
        preprocessing = new PreProcessingImpl();
        segmentation = new SegmentationImpl();
        postprocessing = new PostprocessingImpl();
        featureExtraction = new FeatureExtractionImpl();
        classification = new ClassificationImpl();
    }

    public void performTraining(List<File> files) {
        for (File file : files) {
            processAnImage(ImageUtils.readInImage(file.getPath()));
            featurePayload.setClassName(file.getParentFile().getName().toUpperCase());
            classification.train(featurePayload);
        }
    }

    public String performClassification(File file) {
        processAnImage(ImageUtils.readInImage(file.getPath()));
        return classification.classify(featurePayload);
    }

    /**
     * Clear the previously stored training set.
     */
    public void clearTraining() {
        classification.clearTraining();
    }

    public BufferedImage getOriginal() {
        return original;
    }

    public BufferedImage getPreprocessed() {
        return preprocessed;
    }

    public BufferedImage getSegmented() {
        return segmented;
    }

    public BufferedImage getPostprocessed() {
        return postprocessed;
    }

    public FeaturePayload getFeaturePayload() {
        return featurePayload;
    }

    /**
     * Generates a description of the current pipeline.
     * @return The completed description.
     */
    public PipelineDescription describePipeline() {

        PipelineDescription description = new PipelineDescription();

        description.setPreProcessingDescription(preprocessing.describePipelineStage());
        description.setSegmentationDescription(segmentation.describePipelineStage());
        description.setPostProcessingDescription(postprocessing.describePipelineStage());
        description.setFeatureExtractionDescription(featureExtraction.describePipelineStage());
        description.setClassifierDescription(classification.describePipelineStage());

        return description;
    }

    private void processAnImage(BufferedImage image){
        original = image;
        preprocessed = preprocessing.performPreprocessing(original);
        segmented = segmentation.performSegmentation(preprocessed);
        postprocessed = postprocessing.performPostProcessing(segmented);
        featurePayload = featureExtraction.performFeatureExtraction(postprocessed);
    }
}
