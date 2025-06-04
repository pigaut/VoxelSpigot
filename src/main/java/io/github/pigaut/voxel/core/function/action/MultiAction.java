package io.github.pigaut.voxel.core.function.action;

import io.github.pigaut.voxel.core.function.response.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class MultiAction implements Action {

    private final List<SystemAction> actions;

    public MultiAction(@NotNull List<@NotNull SystemAction> actions) {
        this.actions = actions;
    }

    @Override
    public FunctionResponse dispatch(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        for (SystemAction action : actions) {
            final FunctionResponse response = action.dispatch(player, event, block, target);
            if (response != null) {
                return response;
            }
        }
        return null;
    }

    @Override
    public void execute(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {}

}
