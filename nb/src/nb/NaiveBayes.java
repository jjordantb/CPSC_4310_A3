package nb;

import nb.data.DataSample;
import nb.feature.Feature;
import nb.label.Label;

import java.util.List;

/**
 * @author Parametric on 3/19/2018
 * @project CPSC_4310_A3
 */
public class NaiveBayes {

    private final List<DataSample> dataSamples;
    private final List<Feature> features;

    public NaiveBayes(List<DataSample> dataSamples, List<Feature> features) {
        this.dataSamples = dataSamples;
        this.features = features;
    }



    public int getLabelCount(final Label label) {
        int count = 0;
        for (DataSample sample : this.dataSamples) {
            if (sample.getLabel().equals(label)) {
                count++;
            }
        }
        return count;
    }

    public double getFeatureProbability(final Feature feature, final Label label) {
        final int startCount = this.getFeatureCount(feature);
        if (startCount == 0){
            return 0;
        }
        return ((double) this.getFeatureCountForLabel(feature, label)) / ((double) startCount);
    }

    public int getFeatureCountForLabel(final Feature feature, final Label label) {
        int count = 0;
        for (DataSample sample : this.dataSamples) {
            if (sample.has(feature) && sample.getLabel().equals(label)) {
                count++;
            }
        }
        return count;
    }

    public int getFeatureCount(final Feature feature) {
        int count = 0;
        for (DataSample sample : this.dataSamples) {
            if (sample.has(feature)) {
                count++;
            }
        }
        return count;
    }



}
