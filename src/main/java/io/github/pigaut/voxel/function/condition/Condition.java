package io.github.pigaut.voxel.function.condition;

import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface Condition {

    Condition MET = (player, event, block, target) -> true;
    Condition UNMET = (player, event, block, target) -> false;

    boolean isMet(@Nullable PluginPlayer player, @Nullable Event event, @Nullable Block block, @Nullable Entity target);

}
