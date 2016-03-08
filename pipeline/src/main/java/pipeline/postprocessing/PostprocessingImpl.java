package pipeline.postprocessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pipeline.segmentation.SegmentationImpl;
import qub.visionsystem.ImageOp;
import util.ConfigurationUtils;
import util.StringConstants;

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
     * Constructs a new instance of the PostprocessingImpl pipeline block.
     */
    public PostprocessingImpl() {
        maskSize = Integer.parseInt(ConfigurationUtils.loadProperty(StringConstants.POSTPROCESSING_MASK_SIZE));
    }

    /**
     * Performs an opening.
     * @param source The input image.
     * @return The Image after opening has been performed.
     */
    public BufferedImage PerformOpening(BufferedImage source){
        return ImageOp.open(source, maskSize);
    }

    /**
     * Performs a closing.
     * @param source The input image.
     * @return The Image after opening has been performed.
     */
    public BufferedImage PerformClosing(BufferedImage source){
        return ImageOp.close(source, maskSize);
    }

    /**
     * Performs an opening then a closing.
     * @param source The input image.
     * @return The Image after opening has been performed.
     */
    public BufferedImage PerformOpeningThenClosing(BufferedImage source){
         source = ImageOp.open(source, maskSize);
        return ImageOp.close(source, maskSize);
    }

    /**
     * Performs a closing then an opening.
     * @param source The input image.
     * @return The Image after opening has been performed.
     */
    public BufferedImage PerformClosingThenOpening(BufferedImage source){
        source = ImageOp.close(source, maskSize);
        return ImageOp.open(source, maskSize);
    }

    /**
     * Performs the required post processing on the buffered image.
     * @param bufferedImage The image to perform the processing on.
     * @return The processed image.
     */
    @Override
    public BufferedImage performPostProcessing(BufferedImage bufferedImage) {
        //return PerformOpeningThenClosing(bufferedImage); //Image with holes? run this
        return PerformClosingThenOpening(bufferedImage); //Noisy image? run this
    }

}
