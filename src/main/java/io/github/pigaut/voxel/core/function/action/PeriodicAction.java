package io.github.pigaut.voxel.core.function.action;

import io.github.pigaut.voxel.core.function.response.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class PeriodicAction implements FunctionAction {

    private final EnhancedPlugin plugin;
    private final FunctionAction action;
    private final int interval;
    private final int repetitions;

    public PeriodicAction(EnhancedPlugin plugin, FunctionAction action, int interval, int repetitions) {
        this.plugin = plugin;
        this.action = action;
        this.interval = interval;
        this.repetitions = repetitions;
    }

    @Override
    public @Nullable FunctionResponse dispatch(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        action.dispatch(player, event, block, target);
        for (int i = 1; i < repetitions; i++) {
            final long delay = (long) interval * i;
            plugin.getScheduler().runTaskLater(delay, () -> action.dispatch(player, event, block, target));
        }
        return null;
    }

}
