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

    /**
     * Constructs a new instance of the PreProcessingImpl pipeline block.
     */
    public PreProcessingImpl() {
        neighbourhoodSize = Integer.parseInt(ConfigurationUtils.loadProperty(StringConstants.PREPROCESSING_NEIGHBOURHOOD_SIZE));
        intercept = Integer.parseInt(ConfigurationUtils.loadProperty(StringConstants.PREPROCESSING_INTERCEPT));
    }

    @Override
    public BufferedImage performPreprocessing(BufferedImage bufferedImage) {
        bufferedImage = ImageUtils.enhanceBrightness(bufferedImage, intercept);

        try {
            bufferedImage = ImageUtils.enhanceContrast(bufferedImage);
        } catch (HistogramException e) {
            log.error("Histogram exception", e);
        }

        bufferedImage = ImageUtils.performNoiseReduction(bufferedImage, neighbourhoodSize);
        return bufferedImage;
    }
}
