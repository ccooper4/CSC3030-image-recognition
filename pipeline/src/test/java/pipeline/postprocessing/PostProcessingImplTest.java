package pipeline.postprocessing;

        import org.junit.Before;
        import org.junit.Test;
        import org.junit.Assert;
        import util.ResourceUtils;
        import visionsystem.VisionSystem;

        import javax.imageio.ImageIO;
        import java.awt.image.BufferedImage;
        import java.awt.image.Raster;
        import java.io.File;
        import java.io.IOException;

        import static org.junit.Assert.*;

/**
 * Created by ccoop on 08/03/2016.
 */
public class PostProcessingImplTest {

    public VisionSystem visionSystem;
    public BufferedImage testingImage;
    public BufferedImage postProcessedImage;

    public PostprocessingImpl postprocessing;

    @Before
    public void loadTestingImage() {
        visionSystem = new VisionSystem();
        testingImage = visionSystem.readInImage(ResourceUtils.getResourcePathAsString("segmented.png"));

        postprocessing = new PostprocessingImpl();
    }

    @Test
    public void testPerformPostProcessing() throws IOException {
        BufferedImage result = postprocessing.performPostProcessing(testingImage);

        File outputfile = new File("target/postprocessed.png");
        ImageIO.write(result, "png", outputfile);
    }
}
