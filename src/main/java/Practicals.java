import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            practical2();
//            practical3();
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
     * Part 2 - Enhance the contrast of an image, compare their
     * histograms and display all including the transfer function
     */
    private static void practical2() throws HistogramException {
        // Part 1
        JVision brightnessDisplay = new JVision();
        int brightnessEnhancement = 50;

        BufferedImage image = visionSystem.getPracticalImage("boat256.jpg");                        // the original image
        BufferedImage enhancedImage = visionSystem.enhanceBrightness(image, brightnessEnhancement); // the enhanced image

        visionSystem.displayAnImage(image, brightnessDisplay, 1, 1, "Boat Image");                    // display original image
        visionSystem.displayAnImage(enhancedImage, brightnessDisplay, 601, 1, "Enhanced Boat Image"); // display enhanced image

        visionSystem.createAndDisplayHistogram(image, brightnessDisplay, 1, 301, "Boat Histogram");               // display histogram of original
        visionSystem.createAndDisplayHistogram(enhancedImage, brightnessDisplay, 601, 301, "Enhanced Histogram"); // display histogram of enhanced

        short[] lookUpTable = visionSystem.createBrightnessLut(brightnessEnhancement);              // get the transfer function used
        visionSystem.createAndDisplayGraphPlot(lookUpTable, brightnessDisplay, 301, 1, "Transfer function");  // display the transfer function


        // Part 2
        JVision contrastDisplay = new JVision();
        float gradient = 1.66f;
        float intercept = -80f;

        BufferedImage image1 = visionSystem.getPracticalImage("boat256.jpg");                        // the original image
        BufferedImage enhancedImage1 = visionSystem.enhanceContrast(image1, gradient, intercept); // the enhanced image

        visionSystem.displayAnImage(image1, contrastDisplay, 1, 1, "Boat Image");                    // display original image
        visionSystem.displayAnImage(enhancedImage1, contrastDisplay, 601, 1, "Enhanced Boat Image"); // display enhanced image

        visionSystem.createAndDisplayHistogram(image1, contrastDisplay, 1, 301, "Boat Histogram");               // display histogram of original
        visionSystem.createAndDisplayHistogram(enhancedImage1, contrastDisplay, 601, 301, "Enhanced Histogram"); // display histogram of enhanced

        short[] lookUpTable1 = visionSystem.createLinearStretchLut(gradient, intercept);              // get the transfer function used
        visionSystem.createAndDisplayGraphPlot(lookUpTable1, contrastDisplay, 301, 1, "Transfer function");  // display the transfer function
    }

    private static void practical3() throws HistogramException {

    }

    private static void practical4() throws HistogramException {

    }

    private static void practical5() throws HistogramException {

    }
}


