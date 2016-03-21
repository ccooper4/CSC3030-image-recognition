package pipeline.preprocessing;

import pipeline.IPipelineArtifact;

import java.awt.image.BufferedImage;

public interface IPreprocessing extends IPipelineArtifact {

    BufferedImage performPreprocessing(BufferedImage bufferedImage);
}
