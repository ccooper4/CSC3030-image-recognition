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
        testingImage = visionSystem.readInImage(ResourceUtils.getResourcePathAsString("apple1-066-153.png"));
        //postProcessedImage = visionSystem.readInImage(ResourceUtils.getResourcePathAsString("postprocessed.png"));

        postprocessing = new PostprocessingImpl();
    }

    @Test
    public void testPerformPostProcessing(){
        //TODO
    }


}
