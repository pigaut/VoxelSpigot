package io.github.pigaut.voxel.function;

import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.event.block.*;
import org.jetbrains.annotations.*;

public class BlockClickFunction implements Function {

    private final Action action;
    private final boolean sneaking;
    private final boolean shouldCancel;
    private final Function function;

    public BlockClickFunction(@NotNull Action action, boolean sneaking, boolean shouldCancel, @NotNull Function function) {
        this.action = action;
        this.sneaking = sneaking;
        this.shouldCancel = shouldCancel;
        this.function = function;
    }

    public Action getAction() {
        return action;
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public boolean shouldCancel() {
        return shouldCancel;
    }

    @Override
    public void run(@Nullable PluginPlayer player, @Nullable Block block) {
        function.run(player, block);
    }

}
