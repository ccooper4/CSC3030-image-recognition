package pipeline.segmentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pipeline.BasePipelineArtifact;
import qub.visionsystem.ImageOp;
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
    private float segmentationConstant = 1;

    // </editor-fold>

    // <editor-fold desc="Methods">

    /**
     * Gets the gradient magnitude image for this image.
     * @param image The image.
     * @return The edge detection image.
     */
    private BufferedImage getMagnitudeImage(BufferedImage image) {

        final float[] vertMask = {-1.0f, -2.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, -2.0f, -1.0f }; //iMask

        final float[] horMask = {-1.0f, 0.0f, 1.0f, -2.0f, 0.0f, 2.0f, -1.0f, 0.0f, 1.0f}; //jMask

        BufferedImage vert = ImageOp.convolver(image, vertMask); //i
        BufferedImage hor = ImageOp.convolver(image, horMask); // j

        BufferedImage edges = ImageOp.imagrad(vert, hor);

        return edges;
    }

    /**
     * Applies thresholding directly to the image.
     * @param bufferedImage
     * @return
     */
    private BufferedImage directlySegment(BufferedImage bufferedImage) {

        if (description == "") {
            description = wrapDescription("Brightness based segmentation.");
        }
        int thresholdValue = ImageUtils.calculateAutomaticThresholdValue(bufferedImage, segmentationConstant);

        short[] segmentationLookupUpTable = LUTFactory.segmentationLUT(thresholdValue);

        BufferedImage segmentedImage = ImageUtils.performPixelOp(bufferedImage, segmentationLookupUpTable);

        return segmentedImage;
    }

    /**
     * Implements edge segmentation.
     * @param bufferedImage The buffered image.
     * @return The segmented image.
     */
    private BufferedImage edgeSegment(BufferedImage bufferedImage)
    {
        if (description == "") {
            description = wrapDescription("Edge detection based segmentation");
        }

        BufferedImage edges = getMagnitudeImage(bufferedImage);

        BufferedImage thresholded = directlySegment(edges);

        return thresholded;
    }

    // </editor-fold>

    // <editor-fold desc="Interface Methods">

    /**
     * Performs automatic thresholding of the provided image.
     * @param bufferedImage The buffered image.
     * @return The segmented image.
     */
    @Override
    public BufferedImage performSegmentation(BufferedImage bufferedImage) {

        return directlySegment(bufferedImage);
//        return edgeSegment(bufferedImage);
    }

    /**
     * Describes this pipeline stage.
     * @return
     */
    @Override
    public String describePipelineStage() {
        description += wrapDescription("Automatic - A value = " + segmentationConstant);
        return wrapPipelineStage(description);
    }

    // </editor-fold>
}
