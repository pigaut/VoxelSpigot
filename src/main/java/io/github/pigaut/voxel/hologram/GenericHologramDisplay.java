package io.github.pigaut.voxel.hologram;

import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;
import org.jetbrains.annotations.*;

public abstract class GenericHologramDisplay implements HologramDisplay {

    protected final EnhancedPlugin plugin;
    private final Location location;
    private @Nullable Entity display = null;
    protected @Nullable BukkitTask updateTask = null;

    protected GenericHologramDisplay(@NotNull EnhancedPlugin plugin, @NotNull Location location) {
        this.plugin = plugin;
        this.location = location;
    }

    @Override
    public boolean exists() {
        return display != null && display.isValid();
    }

    public abstract @NotNull Entity create();

    @Override
    public void spawn() {
        if (exists()) {
            throw new IllegalStateException("Cannot spawn an hologram that already exists.");
        }

        if (!plugin.getHolograms().isRegistered(this)) {
            throw new IllegalStateException("Cannot spawn an hologram after it has been removed.");
        }

        if (!location.getChunk().isLoaded()) {
            return;
        }

        display = create();
    }

    @Override
    public void despawn() {
        if (display != null) {
            if (display.isValid()) {
                display.remove();
            }
            display = null;
        }
        if (updateTask != null) {
            if (!updateTask.isCancelled()) {
                updateTask.cancel();
            }
            updateTask = null;
        }
    }

    @Override
    public void remove() {
        despawn();
        plugin.getHolograms().unregisterHologram(location.getChunk(), this);
    }

}
