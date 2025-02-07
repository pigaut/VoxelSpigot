package io.github.pigaut.voxel.function.action;

import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class MultiAction implements Action {

    private final List<Action> actions;
    private boolean shouldReturn = false;

    public MultiAction(@NotNull List<@NotNull Action> actions) {
        this.actions = actions;
        for (Action action : actions) {
            if (action.shouldReturn()) {
                shouldReturn = true;
            }
        }
    }

    @Override
    public void execute(@Nullable PluginPlayer player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        for (Action action : actions) {
            action.execute(player, event, block, target);
            if (action.shouldReturn()) {
                return;
            }
        }
    }

    @Override
    public boolean shouldReturn() {
        return shouldReturn;
    }

}
