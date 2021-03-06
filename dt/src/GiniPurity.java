
import java.util.List;
import java.util.stream.Collectors;

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
