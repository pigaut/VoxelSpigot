package io.github.pigaut.voxel.function;

import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

import java.util.*;

@FunctionalInterface
public interface Function {

    /**
     * Executes the function and returns whether it completed successfully or was interrupted.
     *
     * @param player The player involved, can be null.
     * @param event The event triggering the function, can be null.
     * @param block The block involved, can be null.
     * @param target The target entity, can be null.
     * @return true if the function executed completely, false if interrupted by a return action.
     */
    boolean run(@Nullable PluginPlayer player, @Nullable Event event, @Nullable Block block, @Nullable Entity target);

    default void run() {
        run(null, null, null, null);
    }

    default void run(@NotNull Event event) {
        run(null, event, null, null);
    }

    default void run(@NotNull PluginPlayer player) {
        run(player, null, null, null);
    }

    default void run(@NotNull Block block) {
        run(null, null, block, null);
    }

    default void run(@NotNull PluginPlayer player, @NotNull Event event, @NotNull Block targetBlock) {
        run(player, event, targetBlock, null);
    }

    default void run(@NotNull PluginPlayer player, @NotNull Event event, @NotNull Entity targetEntity) {
        run(player, event, null, targetEntity);
    }

}
