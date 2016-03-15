package pipeline.preprocessing;

import pipeline.IPipelineArtifact;
import qub.visionsystem.HistogramException;

import java.awt.image.BufferedImage;

public interface IPreprocessing extends IPipelineArtifact{

    BufferedImage performPreprocessing(BufferedImage bufferedImage) throws HistogramException;
}
