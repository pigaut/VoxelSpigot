package io.github.pigaut.voxel.function;

import io.github.pigaut.voxel.player.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.plugin.*;
import org.jetbrains.annotations.*;

public class DelayedFunction implements Function {

    private final Plugin plugin;
    private final Function function;
    private final int delay;

    public DelayedFunction(Plugin plugin, Function function, int delay) {
        this.plugin = plugin;
        this.function = function;
        this.delay = delay;
    }

    @Override
    public void run(@Nullable PluginPlayer player, @Nullable Block block) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> function.run(player, block), delay);
    }

}
