package reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qub.visionsystem.*;
import util.ResourceUtils;
import util.StringConstants;
import util.image.ImageUtils;

import java.awt.image.BufferedImage;

/**
 * The main class for image processing and working with the Vision System API.
 */
public class PracticalUtils {

    private static final Logger log = LoggerFactory.getLogger(PracticalUtils.class);

    /**
     * Create a {@link GraphPlot} using a histogram
     * @param histogram the {@link Histogram}
     * @return          the graph plot
     */
    public static GraphPlot createGraphPlot(Histogram histogram) {
        return new GraphPlot(histogram);
    }

    /**
     * Create a {@link GraphPlot} using a look up table
     * @param lookUpTable   the look up table
     * @return              the graph plot
     */
    public static GraphPlot createGraphPlot(short[] lookUpTable) {
        return new GraphPlot(lookUpTable);
    }

    /**
     * Display a {@link BufferedImage} on a {@link JVision}
     * @param img       the buffered image
     * @param display   the canvas
     * @param x         the x co-ordinate
     * @param y         the y co-ordinate
     * @param title     the title
     */
    public static void displayAnImage(BufferedImage img, JVision display, int x, int y, String title) {
        display.imdisp(img, title, x, y);
    }

    /**
     * Create and display a {@link Histogram} using a {@link BufferedImage}
     * @param img                   the buffered image
     * @param display               the canvas
     * @param x                     the x co-ordinate
     * @param y                     the y co-ordinate
     * @param title                 the title
     * @throws HistogramException
     */
    public static void createAndDisplayHistogram(BufferedImage img, JVision display, int x, int y, String title) throws HistogramException {
        Histogram histogram = new Histogram(img);       // Get histogram of image
        GraphPlot graphPlot = createGraphPlot(histogram);   // Get graph plot from histogram
        display.imdisp(graphPlot, title, x, y);             // Display the graph plot
    }

    /**
     * Create and display a {@link GraphPlot} using a look up table
     * @param lookUpTable           the look up table
     * @param display               the canvas
     * @param x                     the x co-ordinate
     * @param y                     the x co-ordinate
     * @param title                 the title
     * @throws HistogramException
     */
    public static void createAndDisplayGraphPlot(short[] lookUpTable, JVision display, int x, int y, String title) throws HistogramException {
        GraphPlot gp = createGraphPlot(lookUpTable);
        display.imdisp(gp, title, x, y);
    }

    /**
     * Utility method for accessing practical images
     */
    public static BufferedImage getPracticalImage(String image) {
        return ImageUtils.readInImage(ResourceUtils.getResourcePathAsString(StringConstants.PRACTICALS + image));
    }
}