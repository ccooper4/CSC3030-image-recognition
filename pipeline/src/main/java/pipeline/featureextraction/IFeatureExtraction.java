package pipeline.featureextraction;

import pipeline.IPipelineArtifact;

import java.awt.image.BufferedImage;

public interface IFeatureExtraction extends IPipelineArtifact {

    /**
     * Performs feature extraction on an image.
     * @param bufferedImage The image to extract features from.
     * @return              The extracted features.
     */
    FeaturePayload performFeatureExtraction(BufferedImage bufferedImage);
}
