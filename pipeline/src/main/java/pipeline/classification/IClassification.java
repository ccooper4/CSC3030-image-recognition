package pipeline.classification;

import pipeline.IPipelineArtifact;
import pipeline.featureextraction.FeaturePayload;

public interface IClassification extends IPipelineArtifact {

    void train(FeaturePayload trainingPayload);

    String classify(FeaturePayload testPayload);

    void clearTraining();
}
