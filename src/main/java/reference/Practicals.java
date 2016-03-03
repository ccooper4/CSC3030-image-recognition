package reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qub.visionsystem.HistogramException;
import qub.visionsystem.JVision;
import shared.VisionSystem;

import java.awt.image.BufferedImage;

/**
 * Practical demonstration / reference class
 */
public class Practicals {

    private static VisionSystem visionSystem = new VisionSystem();
    private static final Logger log = LoggerFactory.getLogger(Practicals.class);

    public static void main(String[] args) {

        try {
//            practical1();
//            practical2();
            practical3();
//            practical4();
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

        BufferedImage imageOne = visionSystem.getPracticalImage("dome256.jpg"); // the first image
        visionSystem.displayAnImage(imageOne, jVision, 1, 1, "Dome Image");     // display first image

        BufferedImage imageTwo = visionSystem.getPracticalImage("boat256.jpg"); // the second image
        visionSystem.displayAnImage(imageTwo, jVision, 301, 1, "Boat Image");   // display second image

        visionSystem.createAndDisplayHistogram(imageOne, jVision, 1, 301, "Dome Histogram");    // display histogram of first
        visionSystem.createAndDisplayHistogram(imageTwo, jVision, 301, 301, "Boat Histogram");  // display histogram of second
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

        BufferedImage image = visionSystem.getPracticalImage("boat256.jpg");                        // the original image
        BufferedImage enhancedImage = visionSystem.enhanceBrightness(image, brightnessEnhancement); // the enhanced image

        visionSystem.displayAnImage(image, brightnessDisplay, 1, 1, "Boat Image");                    // display original image
        visionSystem.displayAnImage(enhancedImage, brightnessDisplay, 601, 1, "Enhanced Boat Image"); // display enhanced image

        visionSystem.createAndDisplayHistogram(image, brightnessDisplay, 1, 301, "Boat Histogram");               // display histogram of original
        visionSystem.createAndDisplayHistogram(enhancedImage, brightnessDisplay, 601, 301, "Enhanced Histogram"); // display histogram of enhanced

        short[] lookUpTable = visionSystem.createBrightnessLUT(brightnessEnhancement);                        // get the transfer function used
        visionSystem.createAndDisplayGraphPlot(lookUpTable, brightnessDisplay, 301, 1, "Transfer function");  // display the transfer function

        // Part 2
        JVision contrastDisplay = new JVision();
        contrastDisplay.setTitle("Practical 2 - Contrast enhancement (Linear Stretch)");
        float gradient = 1.66f;
        float intercept = -80f;

        BufferedImage image1 = visionSystem.getPracticalImage("boat256.jpg");                     // the original image
        BufferedImage enhancedImage1 = visionSystem.enhanceContrast(image1, gradient, intercept); // the enhanced image

        visionSystem.displayAnImage(image1, contrastDisplay, 1, 1, "Boat Image");                    // display original image
        visionSystem.displayAnImage(enhancedImage1, contrastDisplay, 601, 1, "Enhanced Boat Image"); // display enhanced image

        visionSystem.createAndDisplayHistogram(image1, contrastDisplay, 1, 301, "Boat Histogram");               // display histogram of original
        visionSystem.createAndDisplayHistogram(enhancedImage1, contrastDisplay, 601, 301, "Enhanced Histogram"); // display histogram of enhanced

        short[] lookUpTable1 = visionSystem.createLinearStretchLUT(gradient, intercept);                     // get the transfer function used
        visionSystem.createAndDisplayGraphPlot(lookUpTable1, contrastDisplay, 301, 1, "Transfer function");  // display the transfer function

        // Part 3
        JVision contrastDisplay1 = new JVision();
        contrastDisplay1.setTitle("Practical 2 - Contrast enhancement (Power Law)");
        float gamma = 2f;

        BufferedImage image2 = visionSystem.getPracticalImage("boat256.jpg");       // the original image
        BufferedImage enhancedImage2 = visionSystem.enhanceContrast(image2, gamma); // the enhanced image

        visionSystem.displayAnImage(image2, contrastDisplay1, 1, 1, "Boat Image");                    // display original image
        visionSystem.displayAnImage(enhancedImage2, contrastDisplay1, 601, 1, "Enhanced Boat Image"); // display enhanced image

        visionSystem.createAndDisplayHistogram(image2, contrastDisplay1, 1, 301, "Boat Histogram");               // display histogram of original
        visionSystem.createAndDisplayHistogram(enhancedImage2, contrastDisplay1, 601, 301, "Enhanced Histogram"); // display histogram of enhanced

        short[] lookUpTable2 = visionSystem.createPowerLawLUT(gamma);                                         // get the transfer function used
        visionSystem.createAndDisplayGraphPlot(lookUpTable2, contrastDisplay1, 301, 1, "Transfer function");  // display the transfer function

        // Part 4
        JVision contrastDisplay2 = new JVision();
        contrastDisplay2.setTitle("Practical 2 - Contrast enhancement (Histogram Equalisation)");

        BufferedImage image3 = visionSystem.getPracticalImage("boat256.jpg");   // the original image
        BufferedImage enhancedImage3 = visionSystem.enhanceContrast(image3);    // the enhanced image

        visionSystem.displayAnImage(image3, contrastDisplay2, 1, 1, "Boat Image");                    // display original image
        visionSystem.displayAnImage(enhancedImage3, contrastDisplay2, 601, 1, "Enhanced Boat Image"); // display enhanced image

        visionSystem.createAndDisplayHistogram(image3, contrastDisplay2, 1, 301, "Boat Histogram");               // display histogram of original
        visionSystem.createAndDisplayHistogram(enhancedImage3, contrastDisplay2, 601, 301, "Enhanced Histogram"); // display histogram of enhanced

        short[] lookUpTable3 = visionSystem.createHistogramEqualisationLUT(visionSystem.createHistogram(enhancedImage3)); // get the transfer function used
        visionSystem.createAndDisplayGraphPlot(lookUpTable3, contrastDisplay2, 301, 1, "Transfer function");              // display the transfer function
    }

    private static void practical3() throws HistogramException {

    }

    private static void practical4() throws HistogramException {

    }

    private static void practical5() throws HistogramException {

    }
}


