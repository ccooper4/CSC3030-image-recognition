package util.image;

import qub.visionsystem.Histogram;
import qub.visionsystem.HistogramException;

public class LUTFactory {

    /**
     * Create a brightness look up table (Enhance brightness) given the y intercept
     * @param intercept the y intercept of the transformation
     * @return          the look up table
     */
    public static short[] brightnessLUT(int intercept) {
        int capacity = 256;
        short[] lookUpTable = new short[capacity];

        for (int i = 0; i < capacity; i++) {
            if (i < -intercept) {
                lookUpTable[i] = 0;
            } else if (i > 255 - intercept) {
                lookUpTable[i] = 255;
            } else {
                lookUpTable[i] = (short) (i + intercept);
            }
        }
        return lookUpTable;
    }

    /**
     * Create a linear stretch look up table (Enhance contrast) given the gradient and the y intercept
     * @param gradient  the gradient of the transformation
     * @param intercept the intercept of the transformation
     * @return          the look up table
     */
    public static short[] linearStretchLUT(float gradient, float intercept) {
        int capacity = 256;
        short[] lookUpTable = new short[capacity];

        for (int i = 0; i < capacity; i++) {
            if (i < -intercept / gradient) {
                lookUpTable[i] = 0;
            } else if (i > ((255 - intercept) / gradient)) {
                lookUpTable[i] = 255;
            } else {
                lookUpTable[i] = (short) (gradient*i + intercept);
            }
        }
        return lookUpTable;
    }

    /**
     * Create a power law look up table (Enhance contrast) given the gamma value
     * @param gamma the gamma value of the transformation
     * @return      the look up table
     */
    public static short[] powerLawLUT(float gamma) {
        final int capacity = 256;
        final int donominatorConstant = 255;
        short[] lookUpTable = new short[capacity];

        for (int i = 0; i < capacity; i++) {
            lookUpTable[i] = (short) (Math.pow(i, gamma) / Math.pow(donominatorConstant, gamma - 1));
        }
        return lookUpTable;
    }

    /**
     * Generates a lookup table that can be used for segmentation.
     * @param threshold The threshold.
     * @return          The segmentation lookup table.
     */
    public static short[] segmentationLUT(int threshold) {

        short[] lookUpTable = new short[256];

        for (int i = 0; i < lookUpTable.length; i++) {
            if (i <= threshold) {
                lookUpTable[i] = 0;
            } else {
                lookUpTable[i] = 255;
            }
        }

        return lookUpTable;
    }

    /**
     * Create a histogram equalisation look up table (Enhance contrast) given a base histogram
     * @param histogram             the histogram
     * @return                      the look up table
     * @throws HistogramException
     */
    public static short[] histogramEqualisationLUT(Histogram histogram) throws HistogramException {
        final int capacity = 256;
        short[] lookUpTable = new short[capacity];

        for (int i = 0; i < capacity; i++) {
            lookUpTable[i] = (short)
                    Math.max(0,
                            (256 * histogram.getCumulativeFrequency(i) / histogram.getNumSamples()) - 1);
        }
        return lookUpTable;
    }
}
