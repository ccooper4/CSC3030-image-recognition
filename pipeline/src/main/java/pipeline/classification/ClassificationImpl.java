package pipeline.classification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pipeline.featureextraction.FeaturePayload;
import util.ConfigurationUtils;
import util.StringConstants;

public class ClassificationImpl implements IClassification {
    /**
     * The logger for this class.
     */
    private static final Logger log = LoggerFactory.getLogger(ClassificationImpl.class);

    /**
     * Constructs a new instance of the ClassificationImpl pipeline block.
     */
    public ClassificationImpl(FeaturePayload inputFeature) {
        k = Integer.parseInt(ConfigurationUtils.loadProperty(StringConstants.K_VALUE));
        this.inputFeaturePayload = inputFeature;
    }

    /**
     * The bisector of mean values
     */
    private double c;

    /**
     * The k value to use for nearest neighbour
     */
    private int k;

    /**
     * The input feature payload
     */
    private FeaturePayload inputFeaturePayload;
}
