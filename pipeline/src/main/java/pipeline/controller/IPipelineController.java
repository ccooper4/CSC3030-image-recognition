package pipeline.controller;

import java.io.File;
import java.util.List;

public interface IPipelineController {

    /**
     *
     * @param files
     */
    void performTraining(List<File> files);

    /**
     *
     * @param file
     * @return
     */
    String performClassification(File file);
}
