package nb.feature;

import nb.data.DataSample;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.partitioningBy;


public interface Feature {

    boolean belongsTo(DataSample dataSample);

    default List<List<DataSample>> split(final List<DataSample> data) {
        List<List<DataSample>> result = new ArrayList<>();
        Map<Boolean, List<DataSample>> split = data.parallelStream().collect(partitioningBy(this::belongsTo));

        if (split.get(true).size() > 0) {
            result.add(split.get(true));
        } else {
            result.add(new ArrayList<>());
        }
        if (split.get(false).size() > 0) {
            result.add(split.get(false));
        } else {
            result.add(new ArrayList<>());
        }
        return result;
    }

}
