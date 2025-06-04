package io.github.pigaut.voxel.core.function.action.entity;

import io.github.pigaut.voxel.core.function.action.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public interface EntityAction extends Action {

    void execute(@NotNull Entity target);

    @Override
    default void execute(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        if (target != null) {
            execute(target);
        }
    }

}
