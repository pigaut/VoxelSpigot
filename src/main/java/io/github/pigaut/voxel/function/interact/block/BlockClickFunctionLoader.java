package io.github.pigaut.voxel.function.interact.block;

import io.github.pigaut.voxel.function.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.event.block.*;
import org.jetbrains.annotations.*;

public class BlockClickFunctionLoader implements ConfigLoader<BlockClickFunction> {

    @Override
    public @NotNull String getProblemDescription() {
        return "Could not load block click function";
    }

    @Override
    public @NotNull BlockClickFunction loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        final Action action = section.get("click", Action.class);
        final boolean sneaking = section.getOptionalBoolean("sneaking").orElse(false);
        final boolean shouldCancel = section.getOptionalBoolean("cancel").orElse(false);
        final Function function = section.load(Function.class);
        return new SimpleBlockClickFunction(action, sneaking, shouldCancel, function);
    }

    @Override
    public @NotNull BlockClickFunction loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        return new MultiBlockClickFunction(sequence.getAll(BlockClickFunction.class));
    }

}
