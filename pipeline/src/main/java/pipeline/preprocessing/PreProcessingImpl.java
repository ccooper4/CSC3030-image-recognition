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

    /**
     * Method to perform brightness enhancement via a LUT.
     */
    public BufferedImage PerformBrightnessEnhancement(BufferedImage bufferedImage, int intercept){
        return ImageUtils.enhanceBrightness(bufferedImage, intercept);
    }

    /**
     * Method to perform contrast enhancement via histogram equalisation.
     */
    public BufferedImage PerformHistogramEqualisation(BufferedImage bufferedImage) throws HistogramException{
        return ImageUtils.enhanceContrast(bufferedImage);
    }

    /**
     * Method to perform noise reduction via the median technique
     */
    public BufferedImage PerformNoiseReduction(BufferedImage bufferedImage, int neighbourhoodSize){
        return ImageUtils.performNoiseReduction(bufferedImage, neighbourhoodSize);
    }

    @Override
    public BufferedImage performPreprocessing(BufferedImage bufferedImage) throws HistogramException {
        bufferedImage = PerformBrightnessEnhancement(bufferedImage, intercept);
        bufferedImage = PerformHistogramEqualisation(bufferedImage);
        bufferedImage = PerformNoiseReduction(bufferedImage, neighbourhoodSize);
        return bufferedImage;
    }
}
