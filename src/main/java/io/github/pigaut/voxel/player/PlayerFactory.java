package io.github.pigaut.voxel.player;

import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface PlayerFactory<P extends PluginPlayer> {

    @NotNull
    P create(Player player);

}
