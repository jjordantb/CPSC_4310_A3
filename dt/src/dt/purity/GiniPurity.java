package dt.purity;

import dt.data.DataSample;
import dt.label.Label;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Parametric on 3/18/2018
 * @project CPSC_4310_A3
 */
public class GiniPurity implements PurityCalculator {

    @Override
    public double calcFor(List<DataSample> dataSamples) {
        List<Label> labels = dataSamples.parallelStream().map(DataSample::getLabel).distinct().collect(Collectors.toList());
        if (labels.size() > 1) {
            double prob = this.getProbability(dataSamples, labels.get(0));
            return 2.0 * prob * (1 - prob);
        }
        if (labels.size() == 1) {
            return 0D;
        }
        throw new IllegalStateException("BAD");
    }

}
