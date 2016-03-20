package pipeline.postprocessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConfigurationUtils;
import util.StringConstants;
import util.image.ImageUtils;

import java.awt.image.BufferedImage;

public class PostprocessingImpl implements IPostprocessing{

    /**
     * The logger for this class.
     */
    private static final Logger log = LoggerFactory.getLogger(PostprocessingImpl.class);

    /**
     * The size of the mask to use
     */
    private int maskSize;

    /**
     * The description of this stage.
     */
    private String stageDescription;

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
    public BufferedImage performOpeningThenClosing(BufferedImage source) {
        stageDescription += "Open then closing with a mask size of " + maskSize + "\n";
        source = ImageUtils.open(source, maskSize);
        return ImageUtils.close(source, maskSize);
    }

    /**
     * Performs a closing then an opening.
     * @param source The input image.
     * @return The Image after opening has been performed.
     */
    public BufferedImage performClosingThenOpening(BufferedImage source){
        stageDescription += "Closing then opening with a mask size of " + maskSize + "\n";
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
        stageDescription = "Post processing";
        //return performOpeningThenClosing(bufferedImage); //Image with holes? run this
        return performClosingThenOpening(bufferedImage); //Noisy image? run this
    }

    /**
     * Describes this stage of the pipeline.
     * @return The description.
     */
    @Override
    public String describePipelineStage() {
        return stageDescription;
    }

}
