package pipeline.segmentation;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import util.ResourceUtils;
import util.image.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;

/**
 * Tests for the segmentation class.
 */
public class SegmentationImplTest {

    public BufferedImage testingImage;
    public BufferedImage segmentedImage;

    public SegmentationImpl segmentation;

    @Before
    public void loadTestingImage() {
        testingImage = ImageUtils.readInImage(ResourceUtils.getResourcePathAsString("apple1-066-153.png"));
        segmentedImage = ImageUtils.readInImage(ResourceUtils.getResourcePathAsString("segmented.png"));

        segmentation = new SegmentationImpl();
    }

    @Test
    public void testPerformSegmentation() throws Exception {

        BufferedImage result = segmentation.performSegmentation(testingImage);

        Raster resultRaster = result.getRaster();
        Raster storedRaster = segmentedImage.getRaster();

        int width = storedRaster.getWidth();
        int height = storedRaster.getHeight();

        for (int hor = 0; hor < width; ++hor)  {
            for (int vert = 0; vert < height; ++vert)  {
                int resultSample = resultRaster.getSample(hor,vert,0);
                int storedSample = storedRaster.getSample(hor,vert,0);
                Assert.assertEquals(resultSample, storedSample);
            }
        }

        File outputfile = new File("target/segmented.png");
        ImageIO.write(result, "png", outputfile);

    }
}