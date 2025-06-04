package io.github.pigaut.voxel.core.function.interact.block;

import io.github.pigaut.voxel.player.*;
import org.bukkit.event.player.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface BlockClickFunction {

    void onBlockClick(@NotNull PlayerState player, @NotNull PlayerInteractEvent event);

}
