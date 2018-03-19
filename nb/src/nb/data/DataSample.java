package nb.data;

import nb.feature.Feature;
import nb.label.Label;

import java.util.Optional;

/**
 * @author Parametric on 3/18/2018
 * @project CPSC_4310_A3
 */
public interface DataSample {

    Optional<Object> getValue(final String key);

    Label getLabel();

    default boolean has(final Feature feature) {
        return feature.belongsTo(this);
    }

}
