
import java.util.Optional;
import java.util.function.Predicate;

public class PredicateFeature<T> implements Feature {

    private final String col;

    private final Predicate<T> predicate;

    private PredicateFeature(String col, Predicate<T> predicate) {
        this.col = col;
        this.predicate = predicate;
    }

    public static <T> Feature create(String col, Predicate<T> pred) {
        return new PredicateFeature<>(col, pred);
    }

    @Override
    public boolean belongsTo(DataSample dataSample) {
        final Optional<Object> opt = dataSample.getValue(col);
        return opt.isPresent() && this.predicate.test((T) opt.get());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PredicateFeature && ((PredicateFeature) obj).col.equals(this.col)
                && this.predicate.equals(((PredicateFeature) obj).predicate);
    }
}
