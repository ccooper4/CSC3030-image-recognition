package pipeline.controller;

import qub.visionsystem.HistogramException;

import java.awt.image.BufferedImage;

public interface IPipelineController {

    /**
     * Process an image using the pipeline.
     * @param image The image to process.
     */
    void processAnImage(BufferedImage image) throws HistogramException;
}
