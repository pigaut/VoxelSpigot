package io.github.pigaut.voxel.core.function.action;

import io.github.pigaut.voxel.core.function.response.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class DelayedAction implements FunctionAction {

    private final EnhancedPlugin plugin;
    private final FunctionAction action;
    private final int delay;

    public DelayedAction(EnhancedPlugin plugin, FunctionAction action, int delay) {
        this.plugin = plugin;
        this.action = action;
        this.delay = delay;
    }

    @Override
    public @Nullable FunctionResponse dispatch(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        plugin.getScheduler().runTaskLater(delay, () -> action.dispatch(player, event, block, target));
        return null;
    }

}
