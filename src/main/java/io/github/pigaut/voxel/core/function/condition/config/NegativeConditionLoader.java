package io.github.pigaut.voxel.core.function.condition.config;

import io.github.pigaut.voxel.core.function.condition.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.load.*;
import org.jetbrains.annotations.*;

public class NegativeConditionLoader implements ConfigLoader<NegativeCondition> {

    @Override
    public @Nullable String getProblemDescription() {
        return "invalid negative condition";
    }

    @Override
    public @NotNull NegativeCondition loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
        return new NegativeCondition(scalar.loadRequired(Condition.class));
    }

    @Override
    public @NotNull NegativeCondition loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        return new NegativeCondition(sequence.loadRequired(Condition.class));
    }

}
