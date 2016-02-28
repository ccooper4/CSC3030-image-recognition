import utils.ResourceUtils;
import java.awt.image.BufferedImage;

/**
 * The main class for image processing and working with the Vision System API.
 */
public class VisionSystem {

    // constants
    private final String PRACTICALS = "practicals/";
    private final String TRAINING = "assignment/training/";
    private final String TEST = "assignment/test/";

    /**
     * Creare a {@link Histogram] using a {@link BufferedImage}
     * @param image                 the buffered image
     * @return                      the histogram
     * @throws HistogramException
     */
    public Histogram createHistogram(BufferedImage image) throws HistogramException {
        return new Histogram(image);
    }

    /**
     * Create a {@link GraphPlot} using a histogram
     * @param histogram the {@link Histogram}
     * @return          the graph plot
     */
    public GraphPlot createGraphPlot(Histogram histogram) {
        return new GraphPlot(histogram);
    }

    /**
     * Create a {@link GraphPlot} using a look up table
     * @param lookUpTable   the look up table
     * @return              the graph plot
     */
    public GraphPlot createGraphPlot(short[] lookUpTable) {
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
    public void displayAnImage(BufferedImage img, JVision display, int x, int y, String title) {
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
    public void createAndDisplayHistogram(BufferedImage img, JVision display, int x, int y, String title) throws HistogramException {
        Histogram histogram = createHistogram(img);         // Get histogram of image
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
    public void createAndDisplayGraphPlot(short[] lookUpTable, JVision display, int x, int y, String title) throws HistogramException {
        GraphPlot gp = createGraphPlot(lookUpTable);
        display.imdisp(gp, title, x, y);
    }

    /**
     * Create a brightness look up table (Enhance brightness) given the y intercept
     * @param intercept the y intercept of the transformation
     * @return          the look up table
     */
    public short[] createBrightnessLut(int intercept) {
        int capacity = 256;
        short[] lookUpTable = new short[capacity];

        for (int i = 0; i < capacity; i++) {
            if (i < -intercept) {
                lookUpTable[i] = 0;
            } else if (i > 255 - intercept) {
                lookUpTable[i] = 255;
            } else {
                lookUpTable[i] = (short) (i + intercept);
            }
        }
        return lookUpTable;
    }

    /**
     * Create a linear stretch look up table (Enhance contrast) given the gradient and the y intercept
     * @param gradient  the gradient of the transformation
     * @param intercept the intercept of the transformation
     * @return          the look up table
     */
    public short[] createLinearStretchLut(float gradient, float intercept)
    {
        int capacity = 256;
        short[] lookUpTable = new short[capacity];

        for (int i = 0; i < capacity; i++) {
            if (i < -intercept / gradient) {
                lookUpTable[i] = 0;
            } else if (i > ((255 - intercept) / gradient)) {
                lookUpTable[i] = 255;
            } else {
                lookUpTable[i] = (short) (gradient*i + intercept);
            }
        }
        return lookUpTable;
    }

    /**
     * Enhance the brightness of a {@link BufferedImage}
     * @param image     the buffered image
     * @param intercept the intercept of the transformation
     * @return          the enhanced image
     */
    public BufferedImage enhanceBrightness(BufferedImage image, int intercept) {
        short[] lookUpTable = createBrightnessLut(intercept);
        return ImageOp.pixelop(image, lookUpTable);

    }

    /**
     * Enhance the contract of a {@link BufferedImage}
     * @param image     the buffered image
     * @param gradient  the gradient of the transformation
     * @param intercept the intercept of the transformation
     * @return          the enhanced image
     */
    public BufferedImage enhanceContrast(BufferedImage image, float gradient, float intercept)
    {
        short[] lookUpTable = createLinearStretchLut(gradient, intercept);
        return ImageOp.pixelop(image, lookUpTable);
    }

    /**
     * Create a {@link BufferedImage} using the path to the file
     * @param filePath  the file path
     * @return          the buffered image
     */
    public BufferedImage readInImage(String filePath) {
        BufferedImage bufferedImage;
        bufferedImage = ImageOp.readInImage(filePath);
        return bufferedImage;
    }

    /**
     * Utility method for accessing practical images
     */
    public BufferedImage getPracticalImage(String image) {
        return readInImage(ResourceUtils.getResourcePath(PRACTICALS + image));
    }

    /**
     * Utility method for accessing test images
     */
    public BufferedImage getTestImage(String image) {
        return readInImage(ResourceUtils.getResourcePath(TEST + image));
    }

    /**
     * Utility method for accessing training images
     */
    public BufferedImage getTrainingImage(String image) {
        return readInImage(ResourceUtils.getResourcePath(TRAINING + image));
    }

}