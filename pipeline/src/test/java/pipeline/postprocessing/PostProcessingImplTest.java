package pipeline.postprocessing;

import org.junit.Before;
import org.junit.Test;
import util.ResourceUtils;
import util.image.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by ccoop on 08/03/2016.
 */
public class PostProcessingImplTest {

    public BufferedImage testingImage;
    public BufferedImage postProcessedImage;

    public PostprocessingImpl postprocessing;

    @Before
    public void loadTestingImage() {
        testingImage = ImageUtils.readInImage(ResourceUtils.getResourcePathAsString("segmented.png"));

        postprocessing = new PostprocessingImpl();
    }

    @Test
    public void testPerformPostProcessing() throws IOException {
        BufferedImage result = postprocessing.performPostProcessing(testingImage);

        File outputfile = new File("target/postprocessed.png");
        ImageIO.write(result, "png", outputfile);
    }
}
