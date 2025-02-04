package io.github.pigaut.voxel.function.interact.block;

import io.github.pigaut.voxel.function.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.event.block.*;
import org.jetbrains.annotations.*;

public class BlockClickFunctionLoader implements ConfigLoader<BlockClickFunction> {

    @Override
    public @NotNull String getProblemDescription() {
        return "invalid block click function";
    }

    @Override
    public @NotNull BlockClickFunction loadFromSection(@NotNull ConfigSection config) throws InvalidConfigurationException {
        final Action action = config.get("click", Action.class);
        final Function function = config.load(Function.class);
        return new SimpleBlockClickFunction(action, function);
    }

    @Override
    public @NotNull BlockClickFunction loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        return new MultiBlockClickFunction(sequence.getAll(BlockClickFunction.class));
    }

}
