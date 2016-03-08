package pipeline.postprocessing;

import pipeline.IPipelineArtifact;

import java.awt.image.BufferedImage;

public interface IPostprocessing extends IPipelineArtifact {

    BufferedImage performPostProcessing(BufferedImage bufferedImage);
}
