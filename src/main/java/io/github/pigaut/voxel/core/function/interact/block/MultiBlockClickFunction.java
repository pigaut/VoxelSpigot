package io.github.pigaut.voxel.core.function.interact.block;

import io.github.pigaut.voxel.player.*;
import org.bukkit.event.player.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class MultiBlockClickFunction implements BlockClickFunction {

    private final List<BlockClickFunction> functions;

    public MultiBlockClickFunction(@NotNull List<@NotNull BlockClickFunction> functions) {
        this.functions = functions;
    }

    @Override
    public void onBlockClick(@NotNull PlayerState player, @NotNull PlayerInteractEvent event) {
        for (BlockClickFunction function : functions) {
            function.onBlockClick(player, event);
        }
    }

}
