package io.github.pigaut.voxel.core.function.condition.event;

import io.github.pigaut.voxel.core.function.condition.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

@FunctionalInterface
public interface EventCondition extends Condition {

    @Nullable Boolean evaluate(@NotNull Event event);

    @Override
    default @Nullable Boolean evaluate(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        return event != null ? evaluate(event) : null;
    }

}
