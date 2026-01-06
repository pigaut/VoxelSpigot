package io.github.pigaut.voxel.core.hologram;

import eu.decentsoftware.holograms.api.*;
import io.github.pigaut.voxel.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.bukkit.Rotation;
import org.bukkit.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class BlockHologram implements Hologram {

    private final EnhancedPlugin plugin;
    private final Material material;

    public BlockHologram(EnhancedPlugin plugin, Material material) {
        this.plugin = plugin;
        this.material = material;
    }

    @Override
    public @Nullable HologramDisplay spawn(Location location, Rotation rotation, Collection<PlaceholderSupplier> placeholders) {
        World world = SpigotLibs.getWorldOrDefault(location);
        Block block = world.getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (block.getType() != Material.AIR) {
            return null;
        }

        HologramDisplay spawnedHologram = new DecentHologramDisplay(plugin, new Location(world, location.getX(), location.getY(), location.getZ())) {
            {
                DHAPI.addHologramLine(display, material);
                display.showAll();
            }
        };

        return spawnedHologram;
    }

}
