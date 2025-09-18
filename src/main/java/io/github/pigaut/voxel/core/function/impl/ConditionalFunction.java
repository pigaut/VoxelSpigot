package io.github.pigaut.voxel.core.function.impl;

import io.github.pigaut.voxel.core.function.*;
import io.github.pigaut.voxel.core.function.action.*;
import io.github.pigaut.voxel.core.function.condition.*;
import io.github.pigaut.voxel.core.function.response.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class ConditionalFunction implements Function {

    private final String name;
    private final String group;
    private final Condition condition;
    private final SystemAction success;
    private final SystemAction failure;

    public ConditionalFunction(String name, String group, Condition condition, SystemAction success, SystemAction failure) {
        this.name = name;
        this.group = group;
        this.condition = condition;
        this.success = success;
        this.failure = failure;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @Nullable String getGroup() {
        return group;
    }

    @Override
    public FunctionResponse run(@Nullable PlayerState player, @Nullable Event event,
                                @Nullable Block block, @Nullable Entity target) {

        if (condition.isMet(player, event, block, target)) {
            return success.dispatch(player, event, block, target);
        }
        else {
            return failure.dispatch(player, event, block, target);
        }
    }

}
