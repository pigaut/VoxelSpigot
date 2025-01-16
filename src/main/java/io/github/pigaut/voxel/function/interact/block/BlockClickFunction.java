package io.github.pigaut.voxel.function.interact.block;

import io.github.pigaut.voxel.player.*;
import org.bukkit.event.player.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface BlockClickFunction {

    void onBlockClick(@NotNull PluginPlayer player, @NotNull PlayerInteractEvent event);

}
