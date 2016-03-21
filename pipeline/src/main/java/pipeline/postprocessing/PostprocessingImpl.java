package pipeline.postprocessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pipeline.BasePipelineArtifact;
import util.ConfigurationUtils;
import util.StringConstants;
import util.image.ImageUtils;

import java.awt.image.BufferedImage;

public class PostprocessingImpl extends BasePipelineArtifact implements IPostprocessing {

    /**
     * The logger for this class.
     */
    private static final Logger log = LoggerFactory.getLogger(PostprocessingImpl.class);

    /**
     * The size of the mask to use
     */
    private int maskSize;

    /**
     * Constructs a new instance of the PostprocessingImpl pipeline block.
     */
    public PostprocessingImpl() {
        maskSize = Integer.parseInt(ConfigurationUtils.loadProperty(StringConstants.POSTPROCESSING_MASK_SIZE));
    }

    /**
     * Performs an opening then a closing.
     * @param source The input image.
     * @return The Image after opening has been performed.
     */
    private BufferedImage performOpeningThenClosing(BufferedImage source) {
        description += wrapDescription("Opening then closing. Mask size = " + maskSize);
        source = ImageUtils.open(source, maskSize);
        return ImageUtils.close(source, maskSize);
    }

    /**
     * Performs a closing then an opening.
     * @param source The input image.
     * @return The Image after opening has been performed.
     */
    private BufferedImage performClosingThenOpening(BufferedImage source){
        description += wrapDescription("Closing then opening");
        description += wrapDescription("Mask size = " + maskSize);
        source = ImageUtils.close(source, maskSize);
        return ImageUtils.open(source, maskSize);
    }

    /**
     * Performs the required post processing on the buffered image.
     * @param bufferedImage The image to perform the processing on.
     * @return The processed image.
     */
    @Override
    public BufferedImage performPostProcessing(BufferedImage bufferedImage) {
        description = "";
        //return performOpeningThenClosing(bufferedImage); //Image with holes? run this
        return performClosingThenOpening(bufferedImage); //Noisy image? run this
    }

    /**
     * Describes this stage of the pipeline.
     * @return The description.
     */
    @Override
    public String describePipelineStage() {
        return wrapPipelineStage(description);
    }

}
