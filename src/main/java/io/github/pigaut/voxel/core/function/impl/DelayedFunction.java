package io.github.pigaut.voxel.core.function.impl;

import io.github.pigaut.voxel.core.function.*;
import io.github.pigaut.voxel.core.function.response.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class DelayedFunction implements Function {

    private final EnhancedPlugin plugin;
    private final Function function;
    private final int delay;

    public DelayedFunction(EnhancedPlugin plugin, Function function, int delay) {
        this.plugin = plugin;
        this.function = function;
        this.delay = delay;
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
    public @Nullable FunctionResponse run(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        plugin.getScheduler().runTaskLater(delay, () -> function.run(player, event, block, target));
        return null;
    }

}
