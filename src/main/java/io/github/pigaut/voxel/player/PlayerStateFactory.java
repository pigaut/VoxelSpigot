package io.github.pigaut.voxel.player;

import io.github.pigaut.voxel.plugin.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface PlayerStateFactory<TPlugin extends EnhancedJavaPlugin, TPlayer extends GenericPlayerState> {

    @NotNull
    TPlayer create(TPlugin plugin, Player player);

}
