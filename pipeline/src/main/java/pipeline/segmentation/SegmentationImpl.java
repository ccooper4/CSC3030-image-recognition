package pipeline.segmentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pipeline.BasePipelineArtifact;
import util.ConfigurationUtils;
import util.StringConstants;
import util.image.ImageUtils;
import util.image.LUTFactory;

import java.awt.image.BufferedImage;

/**
 * Provides an implementation for the Segmentation stage of the Image Pipeline.
 */
public class SegmentationImpl extends BasePipelineArtifact implements ISegmentation {

    // <editor-fold desc="Constructor">

    /**
     * Constructs a new instance of the segmentation pipeline block.
     */
    public SegmentationImpl() {

        segmentationConstant = Float.parseFloat(ConfigurationUtils.loadProperty(StringConstants.SEGMENTATION_CONSTANT_SETTING));

    }

    // </editor-fold>

    // <editor-fold desc="Fields">

    /**
     * The logger for this class.
     */
    private static final Logger log = LoggerFactory.getLogger(SegmentationImpl.class);

    /**
     * The constant value of A to be used for segmentation.
     */
    private float segmentationConstant = 1.0f;

    // </editor-fold>

    // <editor-fold desc="Methods">

    // </editor-fold>

    // <editor-fold desc="Interface Methods">

    /**
     * Performs automatic thresholding of the provided image.
     * @param bufferedImage The buffered image.
     * @return The segmented image.
     */
    @Override
    public BufferedImage performSegmentation(BufferedImage bufferedImage) {

        int thresholdValue = ImageUtils.calculateAutomaticThresholdValue(bufferedImage, segmentationConstant);

        short[] segmentationLookupUpTable = LUTFactory.segmentationLUT(thresholdValue);

        BufferedImage segmentedImage = ImageUtils.performPixelOp(bufferedImage, segmentationLookupUpTable);

        return segmentedImage;
    }

    /**
     * Describes this pipeline stage.
     * @return
     */
    @Override
    public String describePipelineStage() {
        description = wrapDescription("Automatic - A value = " + segmentationConstant);
        return wrapPipelineStage(description);
    }

    // </editor-fold>
}
