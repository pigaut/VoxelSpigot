package io.github.pigaut.voxel.function;

import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class MultiFunction implements Function {

    private final List<Function> functions;

    public MultiFunction(@NotNull List<@NotNull Function> functions) {
        this.functions = functions;
    }

    @Override
    public void run(@Nullable PluginPlayer player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        for (Function function : functions) {
            function.run(player, event, block, target);
        }
    }

}
