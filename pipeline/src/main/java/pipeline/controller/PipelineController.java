package pipeline.controller;

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

import java.awt.image.BufferedImage;

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

    @Override
    public void processAnImage(BufferedImage image) {
        original = image;
        preprocessed = preprocessing.performPreprocessing(original);
        segmented = segmentation.performSegmentation(preprocessed);
        postprocessed = postprocessing.performPostProcessing(segmented);
        featurePayload = featureExtraction.performFeatureExtraction(postprocessed);
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
}
