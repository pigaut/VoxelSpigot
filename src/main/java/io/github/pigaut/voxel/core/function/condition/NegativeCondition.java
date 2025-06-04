package io.github.pigaut.voxel.core.function.condition;

import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class NegativeCondition implements Condition {

    private final Condition condition;

    public NegativeCondition(@NotNull Condition condition) {
        this.condition = condition;
    }

    @Override
    public boolean isMet(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        return !condition.isMet(player, event, block, target);
    }

}
