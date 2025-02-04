package io.github.pigaut.voxel.function;

import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

import java.util.*;

@FunctionalInterface
public interface Function {

    void run(@Nullable PluginPlayer player, @Nullable Event event, @Nullable Block block, @Nullable Entity target);

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

}
