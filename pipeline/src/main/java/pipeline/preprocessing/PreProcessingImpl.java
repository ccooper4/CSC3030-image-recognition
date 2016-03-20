package pipeline.preprocessing;

import util.ConfigurationUtils;
import util.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.image.ImageUtils;
import qub.visionsystem.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class PreProcessingImpl implements IPreprocessing {

    /**
     * The logger for this class.
     */
    private static final Logger log = LoggerFactory.getLogger(PreProcessingImpl.class);

    /**
     * The intercept to use for brightness enhancement.
     */
    private int neighbourhoodSize;

    /**
     * The neighbourhood size to use for noise reduction.
     */
    private int intercept;

    private String description = "";

    /**
     * Constructs a new instance of the PreProcessingImpl pipeline block.
     */
    public PreProcessingImpl() {
        neighbourhoodSize = Integer.parseInt(ConfigurationUtils.loadProperty(StringConstants.PREPROCESSING_NEIGHBOURHOOD_SIZE));
        intercept = Integer.parseInt(ConfigurationUtils.loadProperty(StringConstants.PREPROCESSING_INTERCEPT));
    }

    @Override
    public BufferedImage performPreprocessing(BufferedImage bufferedImage) {

        description = "Preprocessing - ";

        try {
            bufferedImage = enhanceBrightness(bufferedImage);
            bufferedImage = enhanceContrast(bufferedImage);
            bufferedImage = performNoiseReduction(bufferedImage);
        } catch (HistogramException e) {
            log.error("Histogram exception", e);
        }

        return bufferedImage;
    }

    private BufferedImage enhanceBrightness(BufferedImage bufferedImage) {
        description += " Enhanced brightness, intercept of " + intercept;
        return ImageUtils.enhanceBrightness(bufferedImage, intercept);
    }

    private BufferedImage enhanceContrast(BufferedImage bufferedImage) throws HistogramException {
        description += " Enhanced contrast via histogram equalization";
        return ImageUtils.enhanceContrast(bufferedImage);
    }

    private BufferedImage performNoiseReduction(BufferedImage bufferedImage) {
        description += " Reduced noise with a neighbourhood size of " + neighbourhoodSize;
        return ImageUtils.performNoiseReduction(bufferedImage, neighbourhoodSize);
    }



    /**
     * Describes this pipeline stage.
     * @return
     */
    @Override
    public String describePipelineStage() {
        return description;
    }
}
