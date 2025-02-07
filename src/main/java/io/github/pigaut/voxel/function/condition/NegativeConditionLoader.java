package io.github.pigaut.voxel.function.condition;

import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

public class NegativeConditionLoader implements ConfigLoader<NegativeCondition> {

    @Override
    public @Nullable String getProblemDescription() {
        return "invalid negative condition";
    }

    @Override
    public @NotNull NegativeCondition loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
        return new NegativeCondition(scalar.load(Condition.class));
    }

    @Override
    public @NotNull NegativeCondition loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        return new NegativeCondition(section.load(Condition.class));
    }

    @Override
    public @NotNull NegativeCondition loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        return new NegativeCondition(sequence.load(Condition.class));
    }

}
