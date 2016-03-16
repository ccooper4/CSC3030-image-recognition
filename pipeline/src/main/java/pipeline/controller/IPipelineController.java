package pipeline.controller;

import pipeline.featureextraction.FeaturePayload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public interface IPipelineController {

    /**
     * Train the system using a list of files to represent images.
     * @param files The list of files.
     */
    void performTraining(List<File> files);

    /**
     * Perform classification on a single image file.
     * @param file  The file to classify.
     * @return      The classification name.
     */
    String performClassification(File file);

    /**
     * Clear the previously stored training set.
     */
    void clearTraining();

    BufferedImage getOriginal();

    BufferedImage getPreprocessed();

    BufferedImage getSegmented();

    BufferedImage getPostprocessed();

    FeaturePayload getFeaturePayload();
}
