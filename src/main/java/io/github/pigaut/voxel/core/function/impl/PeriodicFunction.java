package io.github.pigaut.voxel.core.function.impl;

import io.github.pigaut.voxel.core.function.*;
import io.github.pigaut.voxel.core.function.response.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class PeriodicFunction implements Function {

    private final EnhancedPlugin plugin;
    private final Function function;
    private final int interval;
    private final int repetitions;

    public PeriodicFunction(EnhancedPlugin plugin, Function function, int interval, int repetitions) {
        this.plugin = plugin;
        this.function = function;
        this.interval = interval;
        this.repetitions = repetitions;
    }

    @Override
    public @NotNull String getName() {
        return function.getName();
    }

    @Override
    public @Nullable String getGroup() {
        return function.getGroup();
    }

    @Override
    public @Nullable FunctionResponse dispatch(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        function.dispatch(player, event, block, target);
        for (int i = 1; i < repetitions; i++) {
            final long delay = (long) interval * i;
            plugin.getScheduler().runTaskLater(delay, () -> function.dispatch(player, event, block, target));
        }
        return null;
    }

}
