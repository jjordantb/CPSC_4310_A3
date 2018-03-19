package nb.data;

import nb.label.Label;
import nb.label.SeedLabel;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Parametric on 3/18/2018
 * @project CPSC_4310_A3
 */
public class SeedDataSample implements DataSample {

    private final Map<String, Object> values;
    private final String colLabel;

    public SeedDataSample(String colLabel, String[] headers, Object... values) {
        this.colLabel = colLabel;
        this.values = new HashMap<>();
        for (int i = 0; i < headers.length; i++) {
            this.values.put(headers[i], values[i]);
        }
    }

    @Override
    public Optional<Object> getValue(String key) {
        return Optional.ofNullable(this.values.get(key));
    }

    @Override
    public Label getLabel() {
        return new SeedLabel(((Double) values.get(this.colLabel)).intValue());
    }

    public static String getSeedName(final int value) {
        return value == 1 ? "Kama" : value == 2 ? "Rosa" : "Canadian";
    }

}
