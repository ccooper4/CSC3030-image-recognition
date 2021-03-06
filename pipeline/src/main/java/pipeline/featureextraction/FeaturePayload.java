package pipeline.featureextraction;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Container for features extracted per image.
 */
public class FeaturePayload {
    private static final Logger log = LoggerFactory.getLogger(FeaturePayload.class);
    private Integer area = null;
    private Integer perimeter = null;
    private Integer compactness = null;

    private Integer textureVariance = null;
    private String className;

    public FeaturePayload() {
    }

    public FeaturePayload(Integer area, Integer perimeter, Integer variance) {
        this.area = area;
        this.perimeter = perimeter;
        this.textureVariance = variance;
        calculateCompactness();
    }

    /**
     *
     * @param className
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Set the area
     * @param area  the area to set
     */
    public void setArea(Integer area) {
        this.area = area;
    }

    /**
     * Set the perimeter
     * @param perimeter the perimeter to set
     */
    public void setPerimeter(Integer perimeter) {
        this.perimeter = perimeter;
    }

    /**
     * Set the compactness.
     * @param compactness   The compactness to set.
     */
    public void setCompactness(Integer compactness) {
        this.compactness = compactness;
    }

    /**
     * Sets the texture variance.
     * @param textureVariance
     */
    public void setTextureVariance(Integer textureVariance) { this.textureVariance = textureVariance; }

    /**
     *
     * @return
     */
    public String getClassName() {
        return className;
    }

    /**
     * Get the area.
     * @return  The area.
     */
    public Integer getArea() {
        return area;
    }

    /**
     * Get the perimeter.
     * @return  The perimeter.
     */
    public Integer getPerimeter() {
        return perimeter;
    }

    /**
     * Gets the texture variance.
     * @return
     */
    public Integer getTextureVariance() { return textureVariance; }

    /**
     * Get the compactness. A measure of how circular an image is.
     * The lower the value of compactness, the more circular the object.
     * @return  The compactness value.
     */
    public Integer getCompactness() {
        if (compactness == null) {
            compactness = calculateCompactness();
        }
        return compactness;
    }

    /**
     * Calculate the compactness of this payload.
     * @return  The Compactness.
     */
    private Integer calculateCompactness() {
        if (compactness != null) {
            return this.compactness;
        } else if (area != null && perimeter != null) {
            compactness = (int) Math.pow(perimeter, 2) / area;
            return compactness;
        } else {
            log.info("Cannot calculate compactness, perimeter and area must first be set");
            return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        FeaturePayload payload = (FeaturePayload) obj;

        return Objects.equals(this.area, payload.getArea())
                && Objects.equals(this.perimeter, payload.getPerimeter())
                && Objects.equals(this.compactness, payload.getCompactness())
                && Objects.equals(this.textureVariance, payload.getTextureVariance())
                && Objects.equals(this.className, payload.getClassName());
    }

    @Override
    public String toString() {
        return "Feature Extraction \n " +
                "Area: " + this.getArea() + " \n" +
                "Perimeter: " + this.getPerimeter() + " \n" +
                "Compactness: " + this.getCompactness() + "\n" +
                "Variance: " + this.getTextureVariance() + "\n" +
                "Class Name: " + this.getClassName();
    }
}
