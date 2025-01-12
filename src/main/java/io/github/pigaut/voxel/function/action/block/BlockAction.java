package io.github.pigaut.voxel.function.action.block;

import io.github.pigaut.voxel.function.action.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface BlockAction extends Action {

    void execute(@NotNull Block block);

    @Override
    default void execute(@Nullable PluginPlayer player, @Nullable Block block) {
        if (block != null) {
            execute(block);
        }
    }

}
