package io.github.pigaut.voxel.core.hologram;

import eu.decentsoftware.holograms.api.*;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.scheduler.*;
import org.jetbrains.annotations.*;

import java.util.*;

public abstract class DecentHologramDisplay implements HologramDisplay {

    protected final EnhancedPlugin plugin;
    protected final Hologram display;
    protected @Nullable BukkitTask updateTask = null;

    protected DecentHologramDisplay(@NotNull EnhancedPlugin plugin, @NotNull Location location) {
        this.plugin = plugin;
        this.display = DHAPI.createHologram(UUID.randomUUID().toString(), location);
    }

    public void update() {
        display.updateAll(true);
    }

    @Override
    public boolean exists() {
        return display.isEnabled();
    }

    @Override
    public void destroy() {
        if (display.isEnabled()) {
            display.destroy();
        }
        if (updateTask != null) {
            if (!updateTask.isCancelled()) {
                updateTask.cancel();
            }
            updateTask = null;
        }
    }

}
