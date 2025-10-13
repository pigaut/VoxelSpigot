package io.github.pigaut.voxel.core.function;

import io.github.pigaut.voxel.core.function.response.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.manager.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public interface Function extends Identifiable {

    @Nullable
    FunctionResponse dispatch(@Nullable PlayerState player,
                              @Nullable Event event,
                              @Nullable Block block,
                              @Nullable Entity target);

    default void run() {
        dispatch(null, null, null, null);
    }

    default void run(@NotNull Event event) {
        dispatch(null, event, null, null);
    }

    default void run(@NotNull PlayerState player) {
        dispatch(player, null, null, null);
    }

    default void run(@NotNull Block block) {
        dispatch(null, null, block, null);
    }

    default void run(@NotNull PlayerState player, @NotNull Event event) {
        dispatch(player, event, null, null);
    }

    default void run(@NotNull PlayerState player, @NotNull Event event, @NotNull Block targetBlock) {
        dispatch(player, event, targetBlock, null);
    }

    default void run(@NotNull PlayerState player, @NotNull Event event, @NotNull Entity targetEntity) {
        dispatch(player, event, null, targetEntity);
    }

}
