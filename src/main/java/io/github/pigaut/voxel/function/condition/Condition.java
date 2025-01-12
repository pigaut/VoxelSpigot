package io.github.pigaut.voxel.function.condition;

import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface Condition {

    Condition MET = (player, block) -> true;
    Condition UNMET = (player, block) -> false;

    boolean isMet(@Nullable PluginPlayer player, @Nullable Block block);

}
