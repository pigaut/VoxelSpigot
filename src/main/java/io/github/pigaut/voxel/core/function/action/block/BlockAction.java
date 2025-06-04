package io.github.pigaut.voxel.core.function.action.block;

import io.github.pigaut.voxel.core.function.action.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface BlockAction extends Action {

    void execute(@NotNull Block block);

    @Override
    default void execute(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        if (block != null) {
            execute(block);
        }
    }

}
