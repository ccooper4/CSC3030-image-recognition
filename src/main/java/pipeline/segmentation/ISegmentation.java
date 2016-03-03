package pipeline.segmentation;

import pipeline.IPipelineArtifact;

import java.awt.image.BufferedImage;

public interface ISegmentation extends IPipelineArtifact {

    BufferedImage performSegmentation(BufferedImage bufferedImage);
}
