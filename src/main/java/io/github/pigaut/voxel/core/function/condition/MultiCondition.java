package io.github.pigaut.voxel.core.function.condition;

import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class MultiCondition implements Condition {

    private final List<Condition> conditions;

    public MultiCondition(@NotNull List<@NotNull Condition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean isMet(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        for (Condition condition : conditions) {
            if (!condition.isMet(player, event, block, target)) {
                return false;
            }
        }
        return true;
    }

}
