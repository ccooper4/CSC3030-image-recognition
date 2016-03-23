package util.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qub.visionsystem.GraphPlot;
import qub.visionsystem.Histogram;
import qub.visionsystem.HistogramException;
import qub.visionsystem.ImageOp;
import util.ResourceUtils;
import util.StringConstants;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.nio.Buffer;

/**
 * Class to hold all image processing logic - to be used in conjunction with pipeline implementations
 * to perform automated image processing. Methods should be accessed statically.
 */
public class ImageUtils {

    private static final Logger log = LoggerFactory.getLogger(ImageUtils.class);

    /**
     * Enhance the brightness of a {@link BufferedImage}
     * @param image     the buffered image
     * @param intercept the intercept of the transformation
     * @return          the enhanced image
     */
    public static BufferedImage enhanceBrightness(BufferedImage image, int intercept) {
        short[] lookUpTable = LUTFactory.brightnessLUT(intercept);
        return ImageOp.pixelop(image, lookUpTable);
    }

    /**
     * Enhance the contract of a {@link BufferedImage} via linear stretching
     * @param image     the buffered image
     * @param gradient  the gradient of the transformation
     * @param intercept the intercept of the transformation
     * @return          the enhanced image
     */
    public static BufferedImage enhanceContrast(BufferedImage image, float gradient, float intercept)
    {
        short[] lookUpTable = LUTFactory.linearStretchLUT(gradient, intercept);
        return ImageOp.pixelop(image, lookUpTable);
    }

    /**
     * Enhance the contrast of a {@link BufferedImage} via power law function
     * @param image the buffered image
     * @param gamma the gamma value of the transformation
     * @return      the enhanced image
     */
    public static BufferedImage enhanceContrast(BufferedImage  image, float gamma) {
        short[] lookUpTable = LUTFactory.powerLawLUT(gamma);
        return ImageOp.pixelop(image, lookUpTable);
    }

    /**
     * Enhance the contrast of a {@link BufferedImage} via histogram equalisation
     * @param image the buffered image
     * @return      the enhanced image
     */
    public static BufferedImage enhanceContrast(BufferedImage image) throws HistogramException {
        Histogram histogram = new Histogram(image);
        short[] lookUpTable = LUTFactory.histogramEqualisationLUT(histogram);
        return ImageOp.pixelop(image, lookUpTable);
    }

    /**
     * Create a mask
     * @return the mask
     */
    public static float[] createMask(int maskSize, float maskValue) {
        float[] mask = new float[maskSize];
        for (int i = 0; i < maskSize; i++) {
            mask[i] = maskValue;
        }
        return mask;
    }

    /**
     * Create a horizontal mask
     * @return  The mask
     */
    public static float[] createHorizontalMask() {
        return new float[] {-10.f, 10.f, 0.f, 0.f};
    }

    /**
     * Create a vertical mask
     * @return  The mask
     */
    public static float[] createVerticalMask() {
        return new float[] {-10.f, 0.f, 10.f, 0.f};
    }

    /**
     * Calculate the non-black area of an image
     * @param image the image
     * @return      the area
     */
    public static int calculateArea(BufferedImage image) {
        Raster raster = image.getRaster();

        int width = image.getWidth();
        int height = image.getHeight();
        int area = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int sample = raster.getSample(j,i,0);
                if (sample != 0) {
                    area++;
                }
            }
        }
        log.info("Calculated area: " + area);
        return area;
    }

    /**
     * Calculate the perimeter of an object in an image.
     * @param image The image.
     * @return      The perimeter.
     */
    public static int calculatePerimeter(BufferedImage image, int originalArea, int maskSize) {
        BufferedImage erodedImage = erode(image, maskSize);

        int erodedArea = calculateArea(erodedImage);
        int perimeter = originalArea - erodedArea;

        return perimeter;
    }

    /**
     * Calculates the mean gray value for the image.
     * @param image The image.
     * @return      The calculated mean.
     */
    public static int calculateMeanSegmented(BufferedImage image, BufferedImage segmentedImage) {

        Raster imgRaster = image.getRaster();
        Raster segmentedRaster = segmentedImage.getRaster();

        if (imgRaster.getHeight() != segmentedRaster.getHeight() || imgRaster.getWidth() != segmentedRaster.getWidth()) {
            return 0;
        }

        int width = imgRaster.getWidth();
        int height = imgRaster.getHeight();
        int pixelCount = 0;

        int levelSum = 0;

        for (int hor = 0; hor < width; ++hor)  {
            for (int vert = 0; vert < height; ++vert)  {
                if (segmentedRaster.getSample(hor, vert, 0) != 0) {
                    levelSum += imgRaster.getSample(hor, vert, 0);
                    pixelCount++;
                }
            }
        }

        int avg = levelSum / pixelCount;
        log.info("Calculated segmented mean of: " + avg);
        return avg;
    }

    /**
     * Calculates the standard deviation of an image.
     * @param image The image.
     * @param mean  The mean.
     * @return      The calculated standard deviation.
     */
    public static int calculateStandardDeviationSegmented(BufferedImage image, BufferedImage segmented, int mean) {

        Raster imgRaster = image.getRaster();
        Raster segmentedRaster = segmented.getRaster();

        if (imgRaster.getHeight() != segmentedRaster.getHeight() || imgRaster.getWidth() != segmentedRaster.getWidth()) {
            return 0;
        }

        int width = imgRaster.getWidth();
        int height = imgRaster.getHeight();
        int pixelCount = width * height;

        int levelSum = 0;

        for (int hor = 0; hor < width; ++hor)  {
            for (int vert = 0; vert < height; ++vert)  {
                if (segmentedRaster.getSample(hor, vert,0) != 0) {
                    levelSum += Math.pow((imgRaster.getSample(hor, vert, 0) - mean), 2);
                    pixelCount++;
                }
            }
        }

        int divider = pixelCount - 1;

        int sd = (int)Math.sqrt(levelSum / divider);

        log.info("Calculated segmented standard deviation of: " + sd);
        return sd;
    }

    /**
     * Calculates the mean gray value for the image.
     * @param image The image.
     * @return      The calculated mean.
     */
    public static int calculateMean(BufferedImage image) {

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
        log.info("Calculated mean of: " + avg);
        return avg;
    }

    /**
     * Calculates the standard deviation of an image.
     * @param image The image.
     * @param mean  The mean.
     * @return      The calculated standard deviation.
     */
    public static int calculateStandardDeviation(BufferedImage image, int mean) {

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

        log.info("Calculated standard deviation of: " + sd);
        return sd;
    }

    /**
     * Gets the value of T to be used for automatic thresholding.
     * @param image The image.
     * @param a     The value of a.
     * @return      The threshold value.
     */
    public static int calculateAutomaticThresholdValue(BufferedImage image, float a) {
        int mean = calculateMean(image);
        int sd = calculateStandardDeviation(image, mean);

        int threshold = (int)(mean + a * sd);
        log.info("Segmenatation: Calculated threshold of: " + threshold);
        return threshold;
    }

    /**
     * Reduce the noise of an image using a mask
     * @param image the image
     * @param mask  the mask
     * @return      the noise reduced image
     */
    public static BufferedImage performNoiseReduction(BufferedImage image, float[] mask) {
        return ImageOp.convolver(image, mask);
    }

    /**
     * Reduce the noise of an image using neighbourhood median values
     * @param image             the image
     * @param neighbourhoodSize the neighbourhood size (e.g. 3 = 3x3)
     * @return                  the noise reduced image
     */
    public static BufferedImage performNoiseReduction(BufferedImage image, int neighbourhoodSize) {
        return ImageOp.median(image, neighbourhoodSize);
    }

    /**
     * Extract the edges of an image
     * @param image The image
     * @return      The image with edges extracted
     */
    public static BufferedImage performEdgeExtraction(BufferedImage image) {
        BufferedImage horizontalImage = ImageOp.convolver(image, createHorizontalMask());
        BufferedImage verticalImage = ImageOp.convolver(image, createVerticalMask());
        return ImageOp.imagrad(verticalImage, horizontalImage);
    }

    /**
     * Perform a pixel operation
     * @param image the image
     * @param LUT   the look up table
     * @return      the modified image
     */
    public static BufferedImage performPixelOp(BufferedImage image, short[] LUT) {
        return ImageOp.pixelop(image, LUT);
    }

    /**
     * Performs a closing.
     * @param image The input image.
     * @return      The Image after opening has been performed.
     */
    public static BufferedImage close(BufferedImage image, int maskSize) {
        return ImageOp.close(image, maskSize);
    }

    /**
     * Performs an opening.
     * @param image     The input image.
     * @param maskSize  The mask size.
     * @return          The Image after opening has been performed.
     */
    public static BufferedImage open(BufferedImage image, int maskSize) {
        return ImageOp.open(image, maskSize);
    }

    /**
     * Perform an erosion.
     * @param image     The image to erode.
     * @param maskSize  The mask size.
     * @return          The eroded image.
     */
    public static BufferedImage erode(BufferedImage image, int maskSize) {
        return ImageOp.erode(image, maskSize);
    }

    /**
     * Perform a dilation.
     * @param image     The image to dilate.
     * @param maskSize  The mask size.
     * @return          The dilated image.
     */
    public static BufferedImage dilate(BufferedImage image, int maskSize) {
        return ImageOp.dilate(image, maskSize);
    }

    /**
     * Create a {@link BufferedImage} using the path to the file
     * @param filePath  the file path
     * @return          the buffered image
     */
    public static BufferedImage readInImage(String filePath) {
        BufferedImage bufferedImage;
        bufferedImage = ImageOp.readInImage(filePath);
        return bufferedImage;
    }

    /**
     * Create a graph plot using an image.
     * @param image The image.
     * @return      the graph plot.
     */
    public static GraphPlot createGraphPlot(BufferedImage image) {
        return new GraphPlot(createHistogram(image));
    }

    /**
     * Create a histogram using a buffered image.
     * @param image The image.
     * @return      The histogram.
     */
    public static Histogram createHistogram(BufferedImage image) {
        Histogram histogram = null;

        try {
            histogram = new Histogram(image);
        } catch (HistogramException e) {
            log.error("Error creating histogram", e);
        }

        return histogram;
    }

    /**
     * Utility method for accessing test images
     */
    public static BufferedImage getTestImage(String image) {
        return readInImage(ResourceUtils.getResourcePathAsString(StringConstants.TEST + image));
    }

    /**
     * Utility method for accessing training images
     */
    public static BufferedImage getTrainingImage(String image) {
        return readInImage(ResourceUtils.getResourcePathAsString(StringConstants.TRAINING + image));
    }
}
