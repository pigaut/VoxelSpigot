package io.github.pigaut.voxel.hologram.modern;

import io.github.pigaut.voxel.*;
import io.github.pigaut.voxel.hologram.*;
import io.github.pigaut.voxel.hologram.modern.options.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.util.Rotation;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class ItemHologram implements Hologram {

    private final EnhancedPlugin plugin;
    private final ItemStack item;
    private final ItemDisplayOptions options;

    public ItemHologram(EnhancedPlugin plugin, ItemStack item, ItemDisplayOptions options) {
        this.plugin = plugin;
        this.item = item;
        this.options = options;
    }

    @Override
    public @Nullable HologramDisplay spawn(Location location, Rotation rotation, PlaceholderSupplier... placeholders) {
        final World world = SpigotLibs.getWorldOrDefault(location);
        final Block block = world.getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (block.getType() != Material.AIR) {
            return null;
        }

        final HologramDisplay hologram = new GenericHologramDisplay(plugin, new Location(world, location.getX(), location.getY(), location.getZ())) {
            @Override
            public @NotNull Display create() {
                return (ItemDisplay) SpigotLibs.createItemDisplay(location, item, options, false);
            }
        };

        plugin.getHolograms().registerHologram(location.getChunk(), hologram);
        hologram.spawn();
        return hologram;
    }

}
