package io.github.pigaut.voxel.core.function.impl;

import io.github.pigaut.voxel.core.function.*;
import io.github.pigaut.voxel.core.function.action.*;
import io.github.pigaut.voxel.core.function.response.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class SimpleFunction implements Function {

    private final String name;
    private final String group;
    private final ConfigSection section;
    private final SystemAction action;

    public SimpleFunction(String name, String group, ConfigSection section, SystemAction action) {
        this.name = name;
        this.group = group;
        this.section = section;
        this.action = action;
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
    public @NotNull ConfigField getField() {
        return section;
    }

    @Override
    public @Nullable FunctionResponse run(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        return action.dispatch(player, event, block, target);
    }

}
