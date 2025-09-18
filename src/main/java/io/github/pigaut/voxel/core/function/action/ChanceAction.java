package io.github.pigaut.voxel.core.function.action;

import io.github.pigaut.voxel.core.function.response.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.util.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class ChanceAction implements SystemAction {

    private final SystemAction action;
    private final double chance;

    public ChanceAction(SystemAction action, double chance) {
        this.action = action;
        this.chance = chance;
    }

    @Override
    public @Nullable FunctionResponse dispatch(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        if (Probability.test(chance)) {
            return action.dispatch(player, event, block, target);
        }
        return null;
    }

}
