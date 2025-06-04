package io.github.pigaut.voxel.core.function;

import io.github.pigaut.voxel.core.function.response.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.manager.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public interface Function extends Identifiable {

    /**
     * Executes the function and returns whether it completed successfully or was interrupted.
     *
     * @param player The player involved can be null.
     * @param event The event triggering the function can be null.
     * @param block The block involved can be null.
     * @param target The target entity can be null.
     * @return a function response
     */
    @Nullable
    FunctionResponse run(@Nullable PlayerState player, @Nullable Event event,
                         @Nullable Block block, @Nullable Entity target);

    default void run() {
        run(null, null, null, null);
    }

    default void run(@NotNull Event event) {
        run(null, event, null, null);
    }

    default void run(@NotNull PlayerState player) {
        run(player, null, null, null);
    }

    default void run(@NotNull Block block) {
        run(null, null, block, null);
    }

    default void run(@NotNull PlayerState player, @NotNull Event event, @NotNull Block targetBlock) {
        run(player, event, targetBlock, null);
    }

    default void run(@NotNull PlayerState player, @NotNull Event event, @NotNull Entity targetEntity) {
        run(player, event, null, targetEntity);
    }

}
