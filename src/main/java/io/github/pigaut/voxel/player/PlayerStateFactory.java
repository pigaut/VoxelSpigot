package io.github.pigaut.voxel.player;

import io.github.pigaut.voxel.plugin.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface PlayerStateFactory<P extends PlayerState> {

    @NotNull
    P create(EnhancedPlugin plugin, Player player);

}
