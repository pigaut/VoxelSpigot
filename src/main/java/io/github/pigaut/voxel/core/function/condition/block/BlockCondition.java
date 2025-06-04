package io.github.pigaut.voxel.core.function.condition.block;

import io.github.pigaut.voxel.core.function.condition.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface BlockCondition extends Condition {

    boolean isMet(@NotNull Block block);

    @Override
    default boolean isMet(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        if (block != null) {
            return isMet(block);
        }
        return false;
    }

}
