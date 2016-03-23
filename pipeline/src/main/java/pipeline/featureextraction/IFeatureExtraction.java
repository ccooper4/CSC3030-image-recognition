package pipeline.featureextraction;

import pipeline.IPipelineArtifact;

import java.awt.image.BufferedImage;

public interface IFeatureExtraction extends IPipelineArtifact {

    /**
     * Performs feature extraction on an image.
     * @param segmentedImage The image to extract features from.
     * @param preProcImage The pre processed image.
     * @return              The extracted features.
     */
    FeaturePayload performFeatureExtraction(BufferedImage segmentedImage, BufferedImage preProcImage);
}
