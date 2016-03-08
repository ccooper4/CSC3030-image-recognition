package pipeline.postprocessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pipeline.segmentation.SegmentationImpl;
import qub.visionsystem.ImageOp;
import util.ConfigurationUtils;

import java.awt.image.BufferedImage;

public class PostprocessingImpl implements IPostprocessing{

    /**
     * The logger for this class.
     */
    private static final Logger log = LoggerFactory.getLogger(PostprocessingImpl.class);

    /**
     * The size of the mask to use
     */
    private int maskSize = 3;

    /**
     * Constructs a new instance of the PostprocessingImpl pipeline block.
     */
    public PostprocessingImpl() {
        maskSize = Integer.parseInt(ConfigurationUtils.loadProperty("maskSize"));
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

    @Override
    public BufferedImage performPostProcessing(BufferedImage bufferedImage) {
        return null;
    }

}
