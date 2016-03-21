package pipeline;

public abstract class BasePipelineArtifact {

    protected String description = "";

    protected String wrapDescription(String description) {
        return "<p>" + description + "</p>";
    }

    protected String wrapPipelineStage(String description) {
        return "<html>" + description + "</html>";
    }
}
