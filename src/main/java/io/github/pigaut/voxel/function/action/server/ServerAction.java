package io.github.pigaut.voxel.function.action.server;

import io.github.pigaut.voxel.function.action.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface ServerAction extends Action {

    void execute();

    @Override
    default void execute(@Nullable PluginPlayer player, @Nullable Event event,
                         @Nullable Block block, @Nullable Entity target) {
        execute();
    }

}
