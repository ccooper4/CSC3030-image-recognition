package pipeline.featureextraction;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Container for features extracted per image.
 */
public class FeaturePayload {
    private static final Logger log = LoggerFactory.getLogger(FeaturePayload.class);
    private Integer area = null;
    private Integer perimeter = null;
    private Double compactness= null;

    public FeaturePayload() {
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
        this.perimeter = area;
    }

    /**
     * Get the area
     * @return  the area
     */
    public Integer getArea() {
        return this.area;
    }

    /**
     * Get the perimeter
     * @return  the perimeter
     */
    public Integer getPerimeter() {
        return this.perimeter;
    }

    /**
     * Get the compactness. A measure of how circular an image is
     * The lower the value of compactness, the more circular the object
     * @return  the compactness value
     */
    public Double getCompactness() {
        if (compactness != null) {
            return this.compactness;
        } else if (area != null && perimeter != null) {
            compactness =  Math.pow(perimeter, 2) / area;
            return compactness;
        } else {
            log.info("Cannot calculate compactness, perimeter and area must first be set");
            return null;
        }
    }
}
