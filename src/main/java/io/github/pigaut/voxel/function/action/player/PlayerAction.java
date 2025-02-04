package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.function.action.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface PlayerAction extends Action {

    void execute(@NotNull PluginPlayer player);

    @Override
    default void execute(@Nullable PluginPlayer player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        if (player != null) {
            execute(player);
        }
    }

}
