package io.github.pigaut.voxel.function.condition.server;

import io.github.pigaut.voxel.function.condition.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface ServerCondition extends Condition {

    boolean isMet();

    @Override
    default boolean isMet(@Nullable PluginPlayer player, @Nullable Block block) {
        return isMet();
    }

}
