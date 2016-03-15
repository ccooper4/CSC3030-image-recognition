package pipeline.segmentation;

import org.junit.Before;
import org.junit.Test;
import pipeline.BasePipelineTest;
import util.ResourceUtils;
import util.image.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Tests for the segmentation class.
 */
public class SegmentationImplTest extends BasePipelineTest {

    public BufferedImage testingImage;
    public BufferedImage storedSegmentedImage;

    public SegmentationImpl segmentation;

    @Before
    public void loadTestingImage() {
        testingImage = ImageUtils.readInImage(ResourceUtils.getResourcePathAsString("apple1-066-153.png"));
        storedSegmentedImage = ImageUtils.readInImage(ResourceUtils.getResourcePathAsString("segmented.png"));

        segmentation = new SegmentationImpl();
    }

    @Test
    public void testPerformSegmentation() throws Exception {
        BufferedImage result = segmentation.performSegmentation(testingImage);
//        compareImages(result, storedSegmentedImage);

        File outputfile = new File("target/segmented.png");
        ImageIO.write(result, "png", outputfile);

    }
}