package io.github.pigaut.voxel.hologram;

import eu.decentsoftware.holograms.api.*;
import io.github.pigaut.voxel.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.util.Rotation;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class ItemHologram implements Hologram {

    private final EnhancedPlugin plugin;
    private final ItemStack item;

    public ItemHologram(EnhancedPlugin plugin, ItemStack item) {
        this.plugin = plugin;
        this.item = item;
    }

    @Override
    public @Nullable HologramDisplay spawn(Location location, Rotation rotation, PlaceholderSupplier... placeholders) {
        final World world = SpigotLibs.getWorldOrDefault(location);
        final Block block = world.getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (block.getType() != Material.AIR) {
            return null;
        }

        final HologramDisplay hologram = new DecentHologramDisplay(plugin, new Location(world, location.getX(), location.getY(), location.getZ())) {
            {
                DHAPI.addHologramLine(display, item);
                display.showAll();
            }
        };

        return hologram;
    }

}
