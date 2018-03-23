
import java.util.Optional;

public interface DataSample {

    Optional<Object> getValue(final String key);

    Label getLabel();

    default boolean has(final Feature feature) {
        return feature.belongsTo(this);
    }

}
