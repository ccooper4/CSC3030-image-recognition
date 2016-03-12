package pipeline;

import org.junit.Assert;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public abstract class BasePipelineTest {

    protected void compareImages(BufferedImage result, BufferedImage stored) {
        Raster resultRaster = result.getRaster();
        Raster storedRaster = stored.getRaster();

        int width = storedRaster.getWidth();
        int height = storedRaster.getHeight();

        for (int hor = 0; hor < width; ++hor)  {
            for (int vert = 0; vert < height; ++vert)  {
                int resultSample = resultRaster.getSample(hor,vert,0);
                int storedSample = storedRaster.getSample(hor,vert,0);
                Assert.assertEquals(resultSample, storedSample);
            }
        }
    }
}

