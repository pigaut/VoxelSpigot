package io.github.pigaut.voxel.function;

import io.github.pigaut.voxel.function.action.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class SimpleFunction implements Function {

    private final Action action;

    public SimpleFunction(Action action) {
        this.action = action;
    }

    @Override
    public boolean run(@Nullable PluginPlayer player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        action.execute(player, event, block, target);
        return !action.shouldReturn();
    }

}
