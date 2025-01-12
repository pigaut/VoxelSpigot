package io.github.pigaut.voxel.function;

import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

import java.util.*;

@FunctionalInterface
public interface Function {

    void run(@Nullable PluginPlayer player, @Nullable Block block);

    default void run() {
        run(null, null);
    }

    default void run(@NotNull PluginPlayer player) {
        run(player, null);
    }

    default void run(@NotNull Block block) {
        run(null, block);
    }

    static void runAll(PluginPlayer player, Block block, Collection<? extends Function> functions) {
        for (Function function : functions) {
            function.run(player, block);
        }
    }

}
