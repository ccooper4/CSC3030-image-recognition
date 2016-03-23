package pipeline.featureextraction;

import pipeline.BasePipelineArtifact;
import qub.visionsystem.ImageOp;
import util.ConfigurationUtils;
import util.StringConstants;
import util.image.ImageUtils;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class FeatureExtractionImpl extends BasePipelineArtifact implements IFeatureExtraction {

    private int perimeterMask = 0;

    public FeatureExtractionImpl() {
        perimeterMask = Integer.parseInt(ConfigurationUtils.loadProperty(StringConstants.PERIMETER_MASK_SIZE));
    }

    /**
     * Performs feature extraction on an image.
     * @param segmentedImage The image to extract features from.
     * @param preProcImage The pre processed image.
     * @return              The extracted features.
     */
    @Override
    public FeaturePayload performFeatureExtraction(BufferedImage segmentedImage, BufferedImage preProcImage) {
        int area = ImageUtils.calculateArea(segmentedImage);
        int perimeter = ImageUtils.calculatePerimeter(segmentedImage, area, perimeterMask);

        int mean = ImageUtils.calculateMeanSegmented(preProcImage, segmentedImage);
        int sd = ImageUtils.calculateStandardDeviationSegmented(preProcImage, segmentedImage, mean);
        int textureVariance = (int)Math.pow(sd, 2);

        return new FeaturePayload(area, perimeter, textureVariance);
    }

    /**
     * Describes this stage of the pipeline.
     * @return The description.
     */
    @Override
    public String describePipelineStage() {
        description="";
        description += wrapDescription("Area");
        description += wrapDescription("Perimeter, mask = " + perimeterMask);
        description += wrapDescription("Compactness");
        return wrapPipelineStage(description);
    }
}
