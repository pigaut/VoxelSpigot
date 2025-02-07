package io.github.pigaut.voxel.function.action;

import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface Action {

    Action EMPTY = ((player, event, block, target) -> {});

    void execute(@Nullable PluginPlayer player, @Nullable Event event, @Nullable Block block, @Nullable Entity target);

    default boolean shouldReturn() {
        return false;
    }

}
