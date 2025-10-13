package io.github.pigaut.voxel.config.misc;

import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.amount.*;
import io.github.pigaut.yaml.configurator.load.*;
import org.jetbrains.annotations.*;

public class BlockRangeLoader implements ConfigLoader<BlockRange> {

    @Override
    public @Nullable String getProblemDescription() {
        return "invalid block range";
    }

    @Override
    public @NotNull BlockRange loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        final Amount rangeX = section.get("x", Amount.class).withDefault(Amount.ZERO);
        final Amount rangeY = section.get("y", Amount.class).withDefault(Amount.ZERO);
        final Amount rangeZ = section.get("z", Amount.class).withDefault(Amount.ZERO);
        return new BlockRange(rangeX, rangeY, rangeZ);
    }

}
