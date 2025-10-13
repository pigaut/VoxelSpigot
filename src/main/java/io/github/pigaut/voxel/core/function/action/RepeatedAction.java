package io.github.pigaut.voxel.core.function.action;

import io.github.pigaut.voxel.core.function.response.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class RepeatedAction implements FunctionAction {

    private final FunctionAction action;
    private final int repetitions;

    public RepeatedAction(FunctionAction action, int repetitions) {
        this.action = action;
        this.repetitions = repetitions;
    }

    @Override
    public @Nullable FunctionResponse dispatch(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        for (int i = 0; i < repetitions; i++) {
            final FunctionResponse response = action.dispatch(player, event, block, target);
            if (response != null) {
                return response;
            }
        }
        return null;
    }

}
