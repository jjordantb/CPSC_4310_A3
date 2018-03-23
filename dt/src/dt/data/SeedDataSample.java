package dt.data;

import dt.label.Label;
import dt.label.SeedLabel;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    public static String getSeedName(final Label value) {
        if (value instanceof SeedLabel) {
            return ((SeedLabel) value).getValue() == 1D ? "Kama" : ((SeedLabel) value).getValue() == 2D ? "Rosa" : "Canadian";
        }
        return "Not a Seed Label";
    }

}
