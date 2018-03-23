
import java.util.List;

public interface PurityCalculator {

    double calcFor(final List<DataSample> dataSamples);

    default double getProbability(final List<DataSample> dataSamples, Label pos) {
        return dataSamples.parallelStream().filter(d -> d.getLabel().equals(pos)).count() / dataSamples.size();
    }

}
