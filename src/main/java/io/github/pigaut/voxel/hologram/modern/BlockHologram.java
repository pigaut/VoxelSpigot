package io.github.pigaut.voxel.hologram.modern;

import io.github.pigaut.voxel.*;
import io.github.pigaut.voxel.hologram.*;
import io.github.pigaut.voxel.hologram.modern.options.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.util.Rotation;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class BlockHologram implements Hologram {

    private final EnhancedPlugin plugin;
    private final BlockData blockData;
    private final DisplayOptions options;

    public BlockHologram(EnhancedPlugin plugin, Material material, DisplayOptions options) {
        this.plugin = plugin;
        this.blockData = material.createBlockData();
        this.options = options;
    }

    @Override
    public @Nullable HologramDisplay spawn(Location location, Rotation rotation, PlaceholderSupplier... placeholders) {
        final World world = SpigotLibs.getWorldOrDefault(location);
        final Block block = world.getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (block.getType() != Material.AIR) {
            return null;
        }

        final HologramDisplay hologram = new BlockHologram.BlockHologramDisplay(new Location(world, location.getX(), location.getY(), location.getZ()));
        plugin.getHolograms().registerHologram(location.getChunk(), hologram);
        hologram.spawn();
        return hologram;
    }

    private class BlockHologramDisplay implements HologramDisplay {

        private final Location location;

        private BlockDisplay display = null;

        private BlockHologramDisplay(Location location) {
            this.location = location;
        }

        @Override
        public boolean exists() {
            return display != null && display.isValid();
        }

        @Override
        public void spawn() {
            if (exists()) {
                throw new IllegalStateException("Cannot spawn an hologram that exists already.");
            }

            if (!plugin.getHolograms().isRegistered(this)) {
                throw new IllegalStateException("Cannot spawn an hologram after it has been removed.");
            }

            if (!location.getChunk().isLoaded()) {
                return;
            }

            display = (BlockDisplay) SpigotLibs.createBlockDisplay(location, blockData, options, false);
        }

        @Override
        public void despawn() {
            if (display != null) {
                if (display.isValid()) {
                    display.remove();
                }
                display = null;
            }
        }

        @Override
        public void remove() {
            despawn();
            plugin.getHolograms().unregisterHologram(location.getChunk(), this);
        }

    }

}
