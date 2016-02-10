import java.awt.image.BufferedImage;
import java.net.URL;

public class VisionSystem {
    private ClassLoader classLoader = getClass().getClassLoader();

    public static void main(String[] args) {
        new VisionSystem();
    }

    //constructor
    public VisionSystem() {
        try {
            practical1();
            practical2();
        } catch (Exception e) {
            System.out.println("Error:");
            e.printStackTrace();
        }
    }

    public void practical2() throws HistogramException {
        JVision jVision = new JVision();

        short[] lookUpTable = brightnessLut(50);
        BufferedImage image = ImageOp.readInImage(getImage("boat256.jpg"));
        BufferedImage enhancedImage = ImageOp.pixelop(image, lookUpTable);

        displayAnImage(image, jVision, 1, 1, "Boat Image");
        displayAnImage(enhancedImage, jVision, 601, 1, "Enhanced Boat Image");

        createAndDisplayHistogram(image, jVision, 1, 301, "Boat Histogram");
        createAndDisplayHistogram(enhancedImage, jVision, 601, 301, "Enhanced Histogram");

        GraphPlot gp = new GraphPlot(lookUpTable);
        jVision.imdisp(gp, "Transfer function", 301, 1);
    }

    public void practical1() throws HistogramException {
        JVision jVision = new JVision();

        BufferedImage imageOne = readInImage(getImage("dome256.jpg"));
        displayAnImage(imageOne, jVision, 1, 1, "Dome Image");

        BufferedImage imageTwo = readInImage(getImage("boat256.jpg"));
        displayAnImage(imageTwo, jVision, 301, 1, "Boat Image");

        createAndDisplayHistogram(imageOne, jVision, 1, 301, "Dome Histogram");
        createAndDisplayHistogram(imageTwo, jVision, 301, 301, "Boat Histogram");
    }

    public BufferedImage readInImage(String fileName) {
        BufferedImage bufferedImage;
        bufferedImage = ImageOp.readInImage(fileName);
        return bufferedImage;
    }

    public void displayAnImage(BufferedImage img, JVision display, int x, int y, String title) {
        display.imdisp(img, title, x, y);
    }

    public void createAndDisplayHistogram(BufferedImage img, JVision display, int x, int y, String title) throws HistogramException {
        Histogram histogram = new Histogram(img);
        GraphPlot graphPlot = new GraphPlot(histogram);
        display.imdisp(graphPlot, title, x, y);
    }

    public short[] brightnessLut(int intercept) {
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

    public BufferedImage enhanceBrightness(BufferedImage source) {
        return null;
    }

    // Retrieve an image from the images resource folder
    private String getImage(String image) {
        String resourcePath = null;
        URL resource = classLoader.getResource("images/" + image);
        if (resource != null) {
            resourcePath = resource.getFile();
        } else {
            System.out.println("Resource not found:" + image);
        }
        return resourcePath;
    }
}