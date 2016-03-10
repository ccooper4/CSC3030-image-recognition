package pipeline.featureextraction;

import util.image.ImageUtils;

import java.awt.image.BufferedImage;

public class FeatureExtractionImpl implements IFeatureExtraction {

    public FeatureExtractionImpl() {

    }

    /**
     * Performs feature extraction on an image.
     * @param bufferedImage The image to extract features from.
     * @return              The extracted features.
     */
    @Override
    public FeaturePayload performFeatureExtraction(BufferedImage bufferedImage) {
        FeaturePayload featurePayload = new FeaturePayload();
        featurePayload.setArea(ImageUtils.calculateArea(bufferedImage));
        featurePayload.setPerimeter(ImageUtils.calculatePerimeter(bufferedImage));
        return featurePayload;
    }


}
