package io.github.pigaut.voxel.core.function.impl;

import io.github.pigaut.voxel.core.function.*;
import io.github.pigaut.voxel.core.function.response.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class RepeatedFunction implements Function {

    private final Function function;
    private final int repetitions;

    public RepeatedFunction(Function function, int repetitions) {
        this.function = function;
        this.repetitions = repetitions;
    }

    @Override
    public @NotNull String getName() {
        return function.getName();
    }

    @Override
    public @Nullable String getGroup() {
        return function.getGroup();
    }

    @Override
    public @Nullable FunctionResponse run(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        for (int i = 0; i < repetitions; i++) {
            final FunctionResponse response = function.run(player, event, block, target);
            if (response != null) {
                return response;
            }
        }
        return null;
    }

}
