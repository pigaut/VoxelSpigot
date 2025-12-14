package io.github.pigaut.voxel.plugin.task;

import com.google.common.base.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.server.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class PluginScheduler implements Scheduler {

    private final EnhancedPlugin plugin;

    public PluginScheduler(@NotNull EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    public void runTask(@NotNull Runnable runnable) {
        if (SpigotServer.isFolia()) {
            Bukkit.getGlobalRegionScheduler().execute(plugin, runnable);
        } else {
            Bukkit.getScheduler().runTask(plugin, runnable);
        }
    }

    public void runTaskAsync(@NotNull Runnable runnable) {
        if (SpigotServer.isFolia()) {
            Bukkit.getGlobalRegionScheduler().execute(plugin, runnable);
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
        }
    }

    public @Nullable Task runTaskLater(long delay, @NotNull Runnable runnable) {
        if (delay < 1) {
            runTask(runnable);
            return null;
        }

        if (SpigotServer.isFolia()) {
            return new FoliaTaskWrapper(Bukkit.getGlobalRegionScheduler()
                    .runDelayed(plugin, t -> runnable.run(), delay));
        }
        else {
            return new BukkitTaskWrapper(Bukkit.getScheduler().runTaskLater(plugin, runnable, delay));
        }
    }

    public @Nullable Task runTaskLaterAsync(long delay, @NotNull Runnable runnable) {
        if (delay < 1) {
            runTask(runnable);
            return null;
        }

        if (SpigotServer.isFolia()) {
            return new FoliaTaskWrapper(Bukkit.getGlobalRegionScheduler()
                    .runDelayed(plugin, t -> runnable.run(), delay));
        }
        else {
            return new BukkitTaskWrapper(Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay));
        }
    }

    public @NotNull Task runTaskTimer(long period, @NotNull Runnable runnable) {
        Preconditions.checkArgument(period > 0, "Period must be greater than 0");
        if (SpigotServer.isFolia()) {
            return new FoliaTaskWrapper(Bukkit.getGlobalRegionScheduler()
                    .runAtFixedRate(plugin, t -> runnable.run(), period, period));
        }
        else {
            return new BukkitTaskWrapper(Bukkit.getScheduler().runTaskTimer(plugin, runnable, period, period));
        }
    }

    public @NotNull Task runTaskTimerAsync(long period, @NotNull Runnable runnable) {
        Preconditions.checkArgument(period > 0, "Period must be greater than 0");
        if (SpigotServer.isFolia()) {
            return new FoliaTaskWrapper(Bukkit.getGlobalRegionScheduler()
                    .runAtFixedRate(plugin, t -> runnable.run(), period, period));
        }
        else {
            return new BukkitTaskWrapper(Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, period, period));
        }
    }

    public @NotNull Task runTaskTimer(long delay, long period, @NotNull Runnable runnable) {
        Preconditions.checkArgument(delay > 0, "Delay must be greater than 0");
        Preconditions.checkArgument(period > 0, "Period must be greater than 0");
        if (SpigotServer.isFolia()) {
            return new FoliaTaskWrapper(Bukkit.getGlobalRegionScheduler()
                    .runAtFixedRate(plugin, t -> runnable.run(), delay, period));
        }
        else {
            return new BukkitTaskWrapper(Bukkit.getScheduler().runTaskTimer(plugin, runnable, delay, period));
        }
    }

    public @NotNull Task runTaskTimerAsync(long delay, long period, @NotNull Runnable runnable) {
        Preconditions.checkArgument(delay > 0, "Delay must be greater than 0");
        Preconditions.checkArgument(period > 0, "Period must be greater than 0");
        if (SpigotServer.isFolia()) {
            return new FoliaTaskWrapper(Bukkit.getGlobalRegionScheduler()
                    .runAtFixedRate(plugin, t -> runnable.run(), delay, period));
        }
        else {
            return new BukkitTaskWrapper(Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay, period));
        }
    }

}
