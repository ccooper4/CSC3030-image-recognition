package pipeline.featureextraction;

import pipeline.BasePipelineArtifact;
import util.ConfigurationUtils;
import util.StringConstants;
import util.image.ImageUtils;

import java.awt.image.BufferedImage;

public class FeatureExtractionImpl extends BasePipelineArtifact implements IFeatureExtraction {

    private int perimeterMask = 0;

    public FeatureExtractionImpl() {
        perimeterMask = Integer.parseInt(ConfigurationUtils.loadProperty(StringConstants.PERIMETER_MASK_SIZE));
    }

    /**
     * Performs feature extraction on an image.
     * @param bufferedImage The image to extract features from.
     * @return              The extracted features.
     */
    @Override
    public FeaturePayload performFeatureExtraction(BufferedImage bufferedImage) {
        int area = ImageUtils.calculateArea(bufferedImage);
        int perimeter = ImageUtils.calculatePerimeter(bufferedImage, area, perimeterMask);

        return new FeaturePayload(area, perimeter);
    }

    /**
     * Describes this stage of the pipeline.
     * @return The description.
     */
    @Override
    public String describePipelineStage() {
        description += wrapDescription("Area");
        description += wrapDescription("Perimeter, mask = " + perimeterMask);
        description += wrapDescription("Compactness");
        return wrapPipelineStage(description);
    }
}
