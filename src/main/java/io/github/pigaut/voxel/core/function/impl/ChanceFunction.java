package io.github.pigaut.voxel.core.function.impl;

import io.github.pigaut.voxel.core.function.*;
import io.github.pigaut.voxel.core.function.response.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

import java.io.*;

public class ChanceFunction implements Function {

    private final Function function;
    private final double chance;

    public ChanceFunction(Function function, double chance) {
        this.function = function;
        this.chance = chance;
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
    public @NotNull ConfigField getField() {
        return function.getField();
    }

    @Override
    public @Nullable FunctionResponse run(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        return Probability.test(chance) ? function.run(player, event, block, target) : null;
    }

}
