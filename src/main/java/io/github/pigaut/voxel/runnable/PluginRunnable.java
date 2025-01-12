package io.github.pigaut.voxel.runnable;

import org.bukkit.plugin.*;
import org.bukkit.scheduler.*;

public abstract class PluginRunnable extends BukkitRunnable {

    private final Plugin plugin;

    public PluginRunnable(Plugin plugin) {
        this.plugin = plugin;
    }

    public BukkitTask runTask() {
        return super.runTask(plugin);
    }

    public BukkitTask runTaskLater(long delay) {
        return super.runTaskLater(plugin, delay);
    }

    public BukkitTask runTaskTimer(long period) {
        return super.runTaskTimer(plugin, period, period);
    }

    public BukkitTask runTaskTimer(long delay, long period) {
        return super.runTaskTimer(plugin, delay, period);
    }

    public BukkitTask runTaskAsync() {
        return super.runTaskAsynchronously(plugin);
    }

    public BukkitTask runTaskLaterAsync(long delay) {
        return super.runTaskLaterAsynchronously(plugin, delay);
    }

    public BukkitTask runTaskTimerAsync(long period) {
        return super.runTaskTimerAsynchronously(plugin, period, period);
    }

    public BukkitTask runTaskTimerAsync(long delay, long period) {
        return super.runTaskTimerAsynchronously(plugin, delay, period);
    }

}
