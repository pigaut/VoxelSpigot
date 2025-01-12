package io.github.pigaut.voxel.runnable;

import org.bukkit.*;
import org.bukkit.plugin.*;
import org.bukkit.scheduler.*;

public class PluginScheduler {

    private final Plugin plugin;
    private final BukkitScheduler scheduler = Bukkit.getScheduler();

    public PluginScheduler(Plugin plugin) {
        this.plugin = plugin;
    }

    public BukkitTask runTask(Runnable task) {
        return scheduler.runTask(plugin, task);
    }

    public BukkitTask runTaskLater(long delay, Runnable task) {
        return scheduler.runTaskLater(plugin, task, delay);
    }

    public BukkitTask runTaskTimer(long period, Runnable task) {
        return scheduler.runTaskTimer(plugin, task, period, period);
    }

    public BukkitTask runTaskTimer(long delay, long period, Runnable task) {
        return scheduler.runTaskTimer(plugin, task, delay, period);
    }

    public BukkitTask runTaskAsync(Runnable task) {
        return scheduler.runTaskAsynchronously(plugin, task);
    }

    public BukkitTask runTaskLaterAsync(long delay, Runnable task) {
        return scheduler.runTaskLaterAsynchronously(plugin, task, delay);
    }

    public BukkitTask runTaskTimerAsync(long period, Runnable task) {
        return scheduler.runTaskTimerAsynchronously(plugin, task, period, period);
    }

    public BukkitTask runTaskTimerAsync(long delay, long period, Runnable task) {
        return scheduler.runTaskTimerAsynchronously(plugin, task, delay, period);
    }

}
