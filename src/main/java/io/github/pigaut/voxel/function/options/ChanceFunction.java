package io.github.pigaut.voxel.function.options;

import io.github.pigaut.voxel.function.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.util.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

public class ChanceFunction implements Function {

    private final Function function;
    private final double chance;

    public ChanceFunction(Function function, double chance) {
        this.function = function;
        this.chance = chance;
    }

    @Override
    public void run(@Nullable PluginPlayer player, @Nullable Block block) {
        if (Probability.test(chance)) {
            function.run(player, block);
        }
    }

}
