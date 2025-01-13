package io.github.pigaut.voxel.function.condition;

import io.github.pigaut.voxel.function.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

public class NegativeCondition implements Condition {

    private final Condition condition;

    public NegativeCondition(@NotNull Condition condition) {
        this.condition = condition;
    }

    public static @NotNull Condition of(@Nullable Condition condition) {
        if (condition == null) {
            return Condition.MET;
        }
        return new NegativeCondition(condition);
    }

    @Override
    public boolean isMet(@Nullable PluginPlayer player, @Nullable Block block) {
        return !condition.isMet(player, block);
    }

}
