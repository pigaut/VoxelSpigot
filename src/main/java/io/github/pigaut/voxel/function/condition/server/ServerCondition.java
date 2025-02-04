package io.github.pigaut.voxel.function.condition.server;

import io.github.pigaut.voxel.function.condition.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface ServerCondition extends Condition {

    boolean isMet();

    @Override
    default boolean isMet(@Nullable PluginPlayer player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        return isMet();
    }

}
