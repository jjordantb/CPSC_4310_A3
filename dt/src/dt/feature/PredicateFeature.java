package dt.feature;

import dt.data.DataSample;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author Parametric on 3/18/2018
 * @project CPSC_4310_A3
 */
public class PredicateFeature<T> implements Feature {

    private final String col, label;

    private final Predicate<T> predicate;

    private PredicateFeature(String col, String label, Predicate<T> predicate) {
        this.col = col;
        this.label = label;
        this.predicate = predicate;
    }

    public static <T> Feature create(String col, Predicate<T> pred) {
        return new PredicateFeature<>(col, "LABEL", pred);
    }


    @Override
    public boolean belongsTo(DataSample dataSample) {
        final Optional<Object> opt = dataSample.getValue(col);
        return opt.isPresent() && this.predicate.test((T) opt.get());
    }

    @Override
    public String toString() {
        return this.label;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PredicateFeature && ((PredicateFeature) obj).col.equals(this.col)
                && ((PredicateFeature) obj).label.equals(this.label)
                && this.predicate.equals(((PredicateFeature) obj).predicate);
    }
}
