package io.github.pigaut.voxel.plugin.task;

import com.google.common.base.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.server.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class PluginRegionScheduler implements RegionScheduler {

    private final EnhancedPlugin plugin;
    private final Location location;

    public PluginRegionScheduler(@NotNull EnhancedPlugin plugin, @NotNull Location location) {
        this.plugin = plugin;
        this.location = location;
    }

    @Override
    public void runTask(@NotNull Runnable runnable) {
        if (SpigotServer.isFolia()) {
            Bukkit.getRegionScheduler().execute(plugin, location, runnable);
        } else {
            Bukkit.getScheduler().runTask(plugin, runnable);
        }
    }

    @Override
    public Task runTaskLater(long delay, @NotNull Runnable runnable) {
        if (delay < 1) {
            runTask(runnable);
            return null;
        }

        if (SpigotServer.isFolia()) {
            return new FoliaTaskWrapper(Bukkit.getRegionScheduler()
                    .runDelayed(plugin, location, t -> runnable.run(), delay));
        }
        else {
            return new BukkitTaskWrapper(Bukkit.getScheduler().runTaskLater(plugin, runnable, delay));
        }
    }

    @Override
    public @NotNull Task runTaskTimer(long period, @NotNull Runnable runnable) {
        Preconditions.checkArgument(period > 0, "Period must be greater than 0");
        if (SpigotServer.isFolia()) {
            return new FoliaTaskWrapper(Bukkit.getRegionScheduler()
                    .runAtFixedRate(plugin, location, t -> runnable.run(), period, period));
        }
        else {
            return new BukkitTaskWrapper(Bukkit.getScheduler().runTaskTimer(plugin, runnable, period, period));
        }
    }

    @Override
    public @NotNull Task runTaskTimer(long delay, long period, @NotNull Runnable runnable) {
        Preconditions.checkArgument(delay > 0, "Delay must be greater than 0");
        Preconditions.checkArgument(period > 0, "Period must be greater than 0");
        if (SpigotServer.isFolia()) {
            return new FoliaTaskWrapper(Bukkit.getRegionScheduler()
                    .runAtFixedRate(plugin, location, t -> runnable.run(), delay, period));
        }
        else {
            return new BukkitTaskWrapper(Bukkit.getScheduler().runTaskTimer(plugin, runnable, delay, period));
        }
    }

}
