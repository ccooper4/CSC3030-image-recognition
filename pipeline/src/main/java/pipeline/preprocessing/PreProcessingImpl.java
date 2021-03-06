package pipeline.preprocessing;

import pipeline.BasePipelineArtifact;
import util.ConfigurationUtils;
import util.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.image.ImageUtils;
import qub.visionsystem.*;
import java.awt.image.BufferedImage;

public class PreProcessingImpl extends BasePipelineArtifact implements IPreprocessing {

    /**
     * The logger for this class.
     */
    private static final Logger log = LoggerFactory.getLogger(PreProcessingImpl.class);

    /**
     * The intercept to use for brightness enhancement.
     */
    private int intercept;

    /**
     * The neighbourhood size to use for noise reduction.
     */
    private int neighbourhoodSize;

    /**
     * The gradient and intercept to use for brightness enhancement via linear stretching.
     */
    private float interceptLS;
    private float gradient;
    private float gamma;
    private float[] mask;

    /**
     * Constructs a new instance of the PreProcessingImpl pipeline block.
     */
    public PreProcessingImpl() {
        neighbourhoodSize = Integer.parseInt(ConfigurationUtils.loadProperty(StringConstants.PREPROCESSING_NEIGHBOURHOOD_SIZE));
        intercept = Integer.parseInt(ConfigurationUtils.loadProperty(StringConstants.PREPROCESSING_INTERCEPT));
    }

    @Override
    public BufferedImage performPreprocessing(BufferedImage bufferedImage) {
        description = "";
        try {

            // Use brightness based with this config
            bufferedImage = enhanceBrightness(bufferedImage);
            bufferedImage = enhanceContrast(bufferedImage);
            bufferedImage = performNoiseReduction(bufferedImage);

            // Use edge based with this config
//            bufferedImage = enhanceBrightness(bufferedImage);
//            bufferedImage = enhanceContrast(bufferedImage);
//            bufferedImage = performNoiseReductionMask(bufferedImage);

        } catch (Exception e) {
            log.error("Histogram exception", e);
        }
        return bufferedImage;
    }


    private BufferedImage enhanceBrightness(BufferedImage bufferedImage) {
        description += wrapDescription("Enhanced Brightness (Straight Line) - Intercept = " + intercept);
        return ImageUtils.enhanceBrightness(bufferedImage, intercept);
    }

    private BufferedImage enhanceContrastLS(BufferedImage bufferedImage){
        interceptLS = -80f;
        gradient = 1.66f;
        description += wrapDescription("Enhanced Contrast - Intercept = " + interceptLS);// +" Gradient = " + gradient);
        return ImageUtils.enhanceContrast(bufferedImage, gradient, interceptLS);
    }

    private BufferedImage enhanceContrastPL(BufferedImage bufferedImage){
        gamma = 2f;
        description += wrapDescription("Enhanced Contrast - Gamma = " + gamma);
        return ImageUtils.enhanceContrast(bufferedImage, gamma);
    }

    private BufferedImage enhanceContrast(BufferedImage bufferedImage) throws HistogramException {
        description += wrapDescription("Enhanced Contrast - Histogram Equalisation");
        return ImageUtils.enhanceContrast(bufferedImage);
    }
    private BufferedImage performNoiseReduction(BufferedImage bufferedImage) {
        description += wrapDescription("Reduced Noise - Neighbourhood Size = " + neighbourhoodSize);
        return ImageUtils.performNoiseReduction(bufferedImage, neighbourhoodSize);
    }

    private BufferedImage performNoiseReductionMask(BufferedImage bufferedImage) {
        int maskSize = 4;
        float maskValue = 1/9f;
        mask = ImageUtils.createMask(maskSize, maskValue);
        description += wrapDescription("Reduced Noise using mask");
        description += wrapDescription("Mask size: " + maskSize);
        description += wrapDescription("Mask value: " + maskValue );
        return ImageUtils.performNoiseReduction(bufferedImage, mask);
    }

    /**
     * Describes this pipeline stage.
     * @return
     */
    @Override
    public String describePipelineStage() {
        return wrapPipelineStage(description);
    }
}
