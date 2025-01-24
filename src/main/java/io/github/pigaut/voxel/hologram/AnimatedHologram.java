package io.github.pigaut.voxel.hologram;

import io.github.pigaut.voxel.*;
import io.github.pigaut.voxel.hologram.display.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.server.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

import java.util.*;

public class AnimatedHologram implements Hologram {

    private final EnhancedPlugin plugin;
    private final List<String> frames;
    private final int update;
    private final int intervals;

    public AnimatedHologram(EnhancedPlugin plugin, List<String> frames, int update) {
        this.plugin = plugin;
        this.frames = frames;
        this.update = update;
        this.intervals = frames.size() - 1;
        if (intervals < 1) {
            throw new IllegalArgumentException("Animated hologram needs at least two frames");
        }
    }

    @Override
    public HologramDisplay spawn(Location location, boolean persistent, PlaceholderSupplier... placeholderSuppliers) {
        final World world = SpigotLibs.getWorldOrDefault(location);
        final Block block = world.getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (block.getType() != Material.AIR) {
            return null;
        }
        final HologramDisplay hologram = new AnimatedHologramDisplay(
                new Location(world, location.getX(), location.getY(), location.getZ()),
                persistent, placeholderSuppliers);
        hologram.spawn();
        return hologram;
    }

    private class AnimatedHologramDisplay implements HologramDisplay {

        private final Location location;
        private final boolean persistent;
        private final PlaceholderSupplier[] placeholders;
        private ArmorStand display = null;
        private BukkitTask updateTask = null;

        private AnimatedHologramDisplay(Location location, boolean persistent, PlaceholderSupplier... placeholders) {
            this.location = location;
            this.persistent = persistent;
            this.placeholders = placeholders;
        }

        @Override
        public boolean exists() {
            return display != null && display.isValid();
        }

        @Override
        public void spawn() {
            despawn();
            display = SpigotLibs.createHologram(location, persistent);
            updateTask = new BukkitRunnable() {
                int interval = 0;
                @Override
                public void run() {
                    if (!exists()) {
                        despawn();
                        return;
                    }
                    display.setCustomName(StringPlaceholders.parseAll(frames.get(interval), placeholders));
                    interval = interval >= intervals ? 0 : interval + 1;
                }
            }.runTaskTimer(plugin, 0, update);
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
    }

}
