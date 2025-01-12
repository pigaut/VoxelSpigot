package io.github.pigaut.voxel.function.action;

import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface Action {

    Action EMPTY = (player, block) -> {};

    void execute(@Nullable PluginPlayer player, @Nullable Block block);

}
