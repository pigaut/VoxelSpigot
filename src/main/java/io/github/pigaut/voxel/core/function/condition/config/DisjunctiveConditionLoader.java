package io.github.pigaut.voxel.core.function.condition.config;

import io.github.pigaut.voxel.core.function.condition.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.load.*;
import org.jetbrains.annotations.*;

public class DisjunctiveConditionLoader implements ConfigLoader<DisjunctiveCondition> {

    @Override
    public @Nullable String getProblemDescription() {
        return "invalid disjunctive condition";
    }

    @Override
    public @NotNull DisjunctiveCondition loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        return new DisjunctiveCondition(sequence.getAll(Condition.class));
    }

}
