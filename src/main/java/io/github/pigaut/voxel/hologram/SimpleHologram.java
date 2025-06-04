package io.github.pigaut.voxel.hologram;

import io.github.pigaut.voxel.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.util.Rotation;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

public class SimpleHologram implements Hologram {

    private final EnhancedPlugin plugin;
    private final String displayName;
    private final int update;

    public SimpleHologram(EnhancedPlugin plugin, String displayName, int update) {
        this.plugin = plugin;
        this.displayName = displayName;
        this.update = update;
    }

    @Override
    public HologramDisplay spawn(Location location, Rotation rotation, boolean persistent, PlaceholderSupplier... placeholders) {
        final World world = SpigotLibs.getWorldOrDefault(location);
        final Block block = world.getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (block.getType() != Material.AIR) {
            return null;
        }

        final HologramDisplay hologram = new SimpleHologramDisplay(
                new Location(world, location.getX(), location.getY(), location.getZ()),
                persistent, placeholders);
        hologram.spawn();
        return hologram;
    }

    private class SimpleHologramDisplay implements HologramDisplay {

        private final Location location;
        private final boolean persistent;
        private final PlaceholderSupplier[] placeholders;
        private ArmorStand display = null;
        private BukkitTask updateTask = null;

        private SimpleHologramDisplay(Location location, boolean persistent, PlaceholderSupplier... placeholders) {
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
            if (!location.getChunk().isLoaded()) {
                return;
            }
            display = SpigotLibs.createHologram(StringPlaceholders.parseAll(displayName, placeholders), location, persistent);
            if (update > 0) {
                updateTask = new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!exists()) {
                            despawn();
                            return;
                        }
                        display.setCustomName(StringPlaceholders.parseAll(displayName, placeholders));
                    }
                }.runTaskTimer(plugin, update, update);
            }
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
