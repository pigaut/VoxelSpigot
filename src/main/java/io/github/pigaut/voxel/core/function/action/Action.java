package io.github.pigaut.voxel.core.function.action;

import io.github.pigaut.voxel.core.function.response.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface Action extends SystemAction {

    @Override
    default @Nullable FunctionResponse dispatch(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        execute(player, event, block, target);
        return null;
    }

    void execute(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target);

}
