package reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qub.visionsystem.Histogram;
import qub.visionsystem.HistogramException;
import qub.visionsystem.JVision;
import util.image.ImageUtils;
import util.image.LUTFactory;

import java.awt.image.BufferedImage;

/**
 * Practical demonstration / reference class
 */
public class Practicals {
    private static final Logger log = LoggerFactory.getLogger(Practicals.class);

    public static void main(String[] args) {

        try {
//            practical1();
//            practical2();
//            practical3_1();
//            practical3_2();
//            practical3_3();
            practical4_1();
//            practical5();
        } catch (HistogramException e) {
            log.error("Histogram Exception", e);
        }
    }

    /**
     * Retrieve two images, generate their histograms and display all.
     * @throws HistogramException
     */
    private static void practical1() throws HistogramException {
        JVision jVision = new JVision();
        jVision.setTitle("Practical 1 - Histogram Generation");

        BufferedImage imageOne = PracticalUtils.getPracticalImage("dome256.jpg"); // the first image
        PracticalUtils.displayAnImage(imageOne, jVision, 1, 1, "Dome Image");     // display first image

        BufferedImage imageTwo = PracticalUtils.getPracticalImage("boat256.jpg"); // the second image
        PracticalUtils.displayAnImage(imageTwo, jVision, 301, 1, "Boat Image");   // display second image

        PracticalUtils.createAndDisplayHistogram(imageOne, jVision, 1, 301, "Dome Histogram");    // display histogram of first
        PracticalUtils.createAndDisplayHistogram(imageTwo, jVision, 301, 301, "Boat Histogram");  // display histogram of second
    }

    /**
     * Part 1 - Enhance the brightness of an image, compare their
     * histograms and display all including the transfer function
     *
     * Part 2 - Enhance the contrast of an image using linear stretch, compare
     * their histograms and display all including the transfer function
     *
     * Part 3 - Enhance the contrast of an image using power law, compare
     * their histograms and display all including the transfer function
     *
     * Part 4 - Enhance the contrast of an image using histogram equalisation, compare
     * their histograms and display all including the transfer function
     */
    private static void practical2() throws HistogramException {
        // Part 1
        JVision brightnessDisplay = new JVision();
        brightnessDisplay.setTitle("Practical 2 - Brightness enhancement");
        int brightnessEnhancement = 50;

        BufferedImage image = PracticalUtils.getPracticalImage("boat256.jpg");                        // the original image
        BufferedImage enhancedImage = ImageUtils.enhanceBrightness(image, brightnessEnhancement); // the enhanced image

        PracticalUtils.displayAnImage(image, brightnessDisplay, 1, 1, "Boat Image");                    // display original image
        PracticalUtils.displayAnImage(enhancedImage, brightnessDisplay, 601, 1, "Enhanced Boat Image"); // display enhanced image

        PracticalUtils.createAndDisplayHistogram(image, brightnessDisplay, 1, 301, "Boat Histogram");               // display histogram of original
        PracticalUtils.createAndDisplayHistogram(enhancedImage, brightnessDisplay, 601, 301, "Enhanced Histogram"); // display histogram of enhanced

        short[] lookUpTable = LUTFactory.brightnessLUT(brightnessEnhancement);                        // get the transfer function used
        PracticalUtils.createAndDisplayGraphPlot(lookUpTable, brightnessDisplay, 301, 1, "Transfer function");  // display the transfer function

        // Part 2
        JVision contrastDisplay = new JVision();
        contrastDisplay.setTitle("Practical 2 - Contrast enhancement (Linear Stretch)");
        float gradient = 1.66f;
        float intercept = -80f;

        BufferedImage image1 = PracticalUtils.getPracticalImage("boat256.jpg");                     // the original image
        BufferedImage enhancedImage1 = ImageUtils.enhanceContrast(image1, gradient, intercept); // the enhanced image

        PracticalUtils.displayAnImage(image1, contrastDisplay, 1, 1, "Boat Image");                    // display original image
        PracticalUtils.displayAnImage(enhancedImage1, contrastDisplay, 601, 1, "Enhanced Boat Image"); // display enhanced image

        PracticalUtils.createAndDisplayHistogram(image1, contrastDisplay, 1, 301, "Boat Histogram");               // display histogram of original
        PracticalUtils.createAndDisplayHistogram(enhancedImage1, contrastDisplay, 601, 301, "Enhanced Histogram"); // display histogram of enhanced

        short[] lookUpTable1 = LUTFactory.linearStretchLUT(gradient, intercept);                     // get the transfer function used
        PracticalUtils.createAndDisplayGraphPlot(lookUpTable1, contrastDisplay, 301, 1, "Transfer function");  // display the transfer function

        // Part 3
        JVision contrastDisplay1 = new JVision();
        contrastDisplay1.setTitle("Practical 2 - Contrast enhancement (Power Law)");
        float gamma = 2f;

        BufferedImage image2 = PracticalUtils.getPracticalImage("boat256.jpg");       // the original image
        BufferedImage enhancedImage2 = ImageUtils.enhanceContrast(image2, gamma); // the enhanced image

        PracticalUtils.displayAnImage(image2, contrastDisplay1, 1, 1, "Boat Image");                    // display original image
        PracticalUtils.displayAnImage(enhancedImage2, contrastDisplay1, 601, 1, "Enhanced Boat Image"); // display enhanced image

        PracticalUtils.createAndDisplayHistogram(image2, contrastDisplay1, 1, 301, "Boat Histogram");               // display histogram of original
        PracticalUtils.createAndDisplayHistogram(enhancedImage2, contrastDisplay1, 601, 301, "Enhanced Histogram"); // display histogram of enhanced

        short[] lookUpTable2 = LUTFactory.powerLawLUT(gamma);                                         // get the transfer function used
        PracticalUtils.createAndDisplayGraphPlot(lookUpTable2, contrastDisplay1, 301, 1, "Transfer function");  // display the transfer function

        // Part 4
        JVision contrastDisplay2 = new JVision();
        contrastDisplay2.setTitle("Practical 2 - Contrast enhancement (Histogram Equalisation)");

        BufferedImage image3 = PracticalUtils.getPracticalImage("boat256.jpg");   // the original image
        BufferedImage enhancedImage3 = ImageUtils.enhanceContrast(image3);    // the enhanced image

        PracticalUtils.displayAnImage(image3, contrastDisplay2, 1, 1, "Boat Image");                    // display original image
        PracticalUtils.displayAnImage(enhancedImage3, contrastDisplay2, 601, 1, "Enhanced Boat Image"); // display enhanced image

        PracticalUtils.createAndDisplayHistogram(image3, contrastDisplay2, 1, 301, "Boat Histogram");               // display histogram of original
        PracticalUtils.createAndDisplayHistogram(enhancedImage3, contrastDisplay2, 601, 301, "Enhanced Histogram"); // display histogram of enhanced

        short[] lookUpTable3 = LUTFactory.histogramEqualisationLUT(new Histogram(enhancedImage3)); // get the transfer function used
        PracticalUtils.createAndDisplayGraphPlot(lookUpTable3, contrastDisplay2, 301, 1, "Transfer function");              // display the transfer function
    }

    private static void practical3_1() throws HistogramException {
        JVision noiseReduction = new JVision();
        noiseReduction.setTitle("Practical 3 - Noise reduction using masks");

        BufferedImage image = PracticalUtils.getPracticalImage("boatnois.jpg");
        BufferedImage noiseReducedImage3X3 = ImageUtils
                .performNoiseReduction(image, ImageUtils.createMask(9, 1/9f));
        BufferedImage noiseReducedImage5X5 = ImageUtils
                .performNoiseReduction(image, ImageUtils.createMask(25, 1/9f));

        PracticalUtils.displayAnImage(image, noiseReduction, 1, 1, "Original");
        PracticalUtils.displayAnImage(noiseReducedImage3X3, noiseReduction, 301, 1, "3X3 Mask");
        PracticalUtils.displayAnImage(noiseReducedImage5X5, noiseReduction, 601, 1, "5X5 Mask");
    }

    private static void practical3_2() throws HistogramException {
        JVision noiseReduction = new JVision();
        noiseReduction.setTitle("Practical 3 - Noise reduction using medians");

        BufferedImage image = PracticalUtils.getPracticalImage("boatnois.jpg");
        BufferedImage noiseReducedImage3 = ImageUtils.performNoiseReduction(image, 3);
        BufferedImage noiseReducedImage5 = ImageUtils.performNoiseReduction(image, 5);

        PracticalUtils.displayAnImage(image, noiseReduction, 1, 1, "Original");
        PracticalUtils.displayAnImage(noiseReducedImage3, noiseReduction, 301, 1, "Neighbourhood = 3");
        PracticalUtils.displayAnImage(noiseReducedImage5, noiseReduction, 601, 1, "Neighbourhood = 5");
    }

    private static void practical3_3() throws HistogramException {
        JVision edgeExtraction = new JVision();
        edgeExtraction.setTitle("Practical 3 - Edge Extraction");

        BufferedImage image = PracticalUtils.getPracticalImage("boatnois.jpg");
        BufferedImage edgesExtracted = ImageUtils.performEdgeExtraction(image);

        PracticalUtils.displayAnImage(image, edgeExtraction, 1, 1, "Original");
        PracticalUtils.displayAnImage(edgesExtracted, edgeExtraction, 301, 1, "Edge Extraction");
    }

    private static void practical4_1() throws HistogramException {
        JVision jVision = new JVision();
        jVision.setTitle("Practical 4");

        BufferedImage image = PracticalUtils.getPracticalImage("vehicle3.jpg");
        PracticalUtils.displayAnImage(image, jVision, 1, 1, "Image");
        PracticalUtils.createAndDisplayHistogram(image, jVision, 1, 301, "Histogram");

        BufferedImage preprocessedImage = preprocessAnImage(image);
        PracticalUtils.displayAnImage(preprocessedImage, jVision, 301, 1, "Preprocessed Image");
        PracticalUtils.createAndDisplayHistogram(preprocessedImage, jVision, 301, 301, "Preprocessed histogram");


        BufferedImage thresholdedImage = performAutomaticThresholding(preprocessedImage, -2);
        PracticalUtils.displayAnImage(thresholdedImage, jVision, 601, 1, "Thresholded Image");
        PracticalUtils.createAndDisplayHistogram(thresholdedImage, jVision, 601, 301, "Thresholded histogram");

        BufferedImage postprocessedImage = postprocessAnImage(thresholdedImage, 3);
        PracticalUtils.displayAnImage(postprocessedImage, jVision, 901, 1, "Post-Processed Image");

        ImageUtils.calculateArea(postprocessedImage);
    }

    public static BufferedImage preprocessAnImage(BufferedImage image) throws HistogramException {
        image = ImageUtils.enhanceBrightness(image, 70);
        image = ImageUtils.enhanceContrast(image, 1.66f, -80f);
        image = ImageUtils.performNoiseReduction(image, ImageUtils.createMask(9, 1/9f));
        return image;
    }

    public static BufferedImage performAutomaticThresholding(BufferedImage image, int a){
        int threshold = ImageUtils.calculateAutomaticThresholdValue(image, a);
        short[] LUT = LUTFactory.segmentationLUT(threshold);
        BufferedImage result = ImageUtils.performPixelOp(image, LUT);
        return result;
    }

    public static BufferedImage postprocessAnImage(BufferedImage image, int masksize){
        image = ImageUtils.close(image, masksize);
        image = ImageUtils.open(image, masksize);
        return image;
    }
}

