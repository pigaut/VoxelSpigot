package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import org.jetbrains.annotations.*;

public class DelayedAction implements PlayerAction {

    private final Plugin plugin;
    private final PlayerAction action;
    private final int delay;

    public DelayedAction(Plugin plugin, PlayerAction action, int delay) {
        this.plugin = plugin;
        this.action = action;
        this.delay = delay;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> action.execute(player), delay);
    }

}
