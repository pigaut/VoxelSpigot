package io.github.pigaut.voxel.function.condition.block;

import io.github.pigaut.voxel.function.condition.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface BlockCondition extends Condition {

    boolean isMet(@NotNull Block block);

    @Override
    default boolean isMet(@Nullable PluginPlayer player, @Nullable Block block) {
        if (block != null) {
            return isMet(block);
        }
        return false;
    }

}
