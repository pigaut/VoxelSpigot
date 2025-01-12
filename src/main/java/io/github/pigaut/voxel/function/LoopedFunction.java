package io.github.pigaut.voxel.function;

import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

public class LoopedFunction implements Function {

    private final Function function;
    private final int repetitions;

    public LoopedFunction(Function function, int repetitions) {
        this.function = function;
        this.repetitions = repetitions;
    }

    @Override
    public void run(@Nullable PluginPlayer player, @Nullable Block block) {
        for (int i = 0; i < repetitions; i++) {
            function.run(player);
        }
    }

}
