package pipeline.segmentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qub.visionsystem.ImageOp;
import util.ConfigurationUtils;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

/**
 * Provides an implementation for the Segmentation stage of the Image Pipeline.
 */
public class SegmentationImpl implements ISegmentation {

    // <editor-fold desc="Constructor">

    /**
     * Constructs a new instance of the segmentation pipeline block.
     */
    public SegmentationImpl() {

        segmentationConstant = Integer.parseInt(ConfigurationUtils.loadProperty("segmenatationConstant"));

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
    private int segmentationConstant = 1;

    // </editor-fold>

    // <editor-fold desc="Methods">

    /**
     * Calculates the mean gray value for the image.
     * @param image The image.
     * @return The calculated mean.
     */
    private int calculateMean(BufferedImage image) {

        Raster imgRaster = image.getRaster();

        int width = imgRaster.getWidth();
        int height = imgRaster.getHeight();
        int pixelCount = width * height;

        int levelSum = 0;

        for (int hor = 0; hor < width; ++hor)  {
            for (int vert = 0; vert < height; ++vert)  {
                levelSum += imgRaster.getSample(hor, vert, 0);
            }
        }

        int avg = levelSum / pixelCount;

        return avg;
    }

    /**
     * Calculates the standard deviation of an image.
     * @param image The image.
     * @param mean The mean.
     * @return The calculated standard deviation.
     */
    private int calculateStandardDeviation(BufferedImage image, int mean) {

        Raster imgRaster = image.getRaster();

        int width = imgRaster.getWidth();
        int height = imgRaster.getHeight();
        int pixelCount = width * height;

        int levelSum = 0;

        for (int hor = 0; hor < width; ++hor)  {
            for (int vert = 0; vert < height; ++vert)  {
                levelSum += Math.pow((imgRaster.getSample(hor, vert, 0) - mean), 2);
            }
        }

        int divider = pixelCount - 1;

        int sd = (int)Math.sqrt(levelSum / divider);

        return sd;
    }

    /**
     * Gets the value of T to be used for automatic thresholding.
     * @param image The image.
     * @param a The value of a.
     * @return The threshold value.
     */
    private int getAutomticThresholdValue(BufferedImage image, int a) {

        int mean = calculateMean(image);
        int sd = calculateStandardDeviation(image, mean);

        int threshold = mean + a * sd;

        return threshold;
    }

    /**
     * Generates a lookup table that can be used for segmentation.
     * @param threshold The threshold.
     * @return The segmentation lookup table.
     */
    private short[] getSegmentationLUT(int threshold) {

        short[] lookUpTable = new short[256];

        for (int i = 0; i < lookUpTable.length; i++) {
            if (i < threshold) {
                lookUpTable[i] = 0;
            } else {
                lookUpTable[i] = 255;
            }
        }

        return lookUpTable;
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

        int thresholdValue = getAutomticThresholdValue(bufferedImage, segmentationConstant);

        short[] segmentationLookupUpTable = getSegmentationLUT(thresholdValue);

        BufferedImage segmentedImage = ImageOp.pixelop(bufferedImage, segmentationLookupUpTable);

        return segmentedImage;
    }

    // </editor-fold>
}
