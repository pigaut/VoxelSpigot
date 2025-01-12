package io.github.pigaut.voxel.function.condition.player;

import io.github.pigaut.voxel.function.condition.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface PlayerCondition extends Condition {

    boolean isMet(@NotNull PluginPlayer player);

    @Override
    default boolean isMet(@Nullable PluginPlayer player, @Nullable Block block) {
        if (player != null) {
            return isMet(player);
        }
        return false;
    }

}
