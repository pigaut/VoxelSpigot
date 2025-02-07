package io.github.pigaut.voxel.function.action;

import io.github.pigaut.voxel.player.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class ReturnAction implements Action {

    @Override
    public void execute(@Nullable PluginPlayer player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {}

    @Override
    public boolean shouldReturn() {
        return true;
    }

}
