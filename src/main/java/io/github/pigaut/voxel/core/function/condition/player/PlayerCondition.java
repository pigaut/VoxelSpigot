package io.github.pigaut.voxel.core.function.condition.player;

import io.github.pigaut.voxel.core.function.condition.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface PlayerCondition extends Condition {

    boolean isMet(@NotNull PlayerState player);

    @Override
    default boolean isMet(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        if (player != null) {
            return isMet(player);
        }
        return false;
    }

}
