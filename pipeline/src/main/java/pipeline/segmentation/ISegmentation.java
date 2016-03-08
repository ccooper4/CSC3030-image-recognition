package pipeline.segmentation;

import pipeline.IPipelineArtifact;

import java.awt.image.BufferedImage;

/**
 * Outlines the pipeline stage by which an image can be segmented for feature extraction.
 */
public interface ISegmentation extends IPipelineArtifact {

    /**
     * Performs the segmentation operation on an image.
     * @param bufferedImage The image to segment.
     * @return The segmented image.
     */
    BufferedImage performSegmentation(BufferedImage bufferedImage);
}
