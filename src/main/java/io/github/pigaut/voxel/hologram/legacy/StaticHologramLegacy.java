package io.github.pigaut.voxel.hologram.legacy;

import io.github.pigaut.voxel.*;
import io.github.pigaut.voxel.hologram.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.util.Rotation;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;
import org.jetbrains.annotations.*;

public class StaticHologramLegacy implements Hologram {

    private final EnhancedPlugin plugin;
    private final String displayName;
    private final int update;

    public StaticHologramLegacy(EnhancedPlugin plugin, String displayName, int update) {
        this.plugin = plugin;
        this.displayName = displayName;
        this.update = update;
    }

    @Override
    public HologramDisplay spawn(Location location, Rotation rotation, PlaceholderSupplier... placeholders) {
        final World world = SpigotLibs.getWorldOrDefault(location);
        final Block block = world.getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (block.getType() != Material.AIR) {
            return null;
        }

        final HologramDisplay hologram = new GenericHologramDisplay(plugin, new Location(world, location.getX(), location.getY(), location.getZ())) {
            @Override
            public @NotNull Entity create() {
                final ArmorStand display = SpigotLibs.createHologram(StringPlaceholders.parseAll(displayName, placeholders), location, false);
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
                return display;
            }
        };

        plugin.getHolograms().registerHologram(location.getChunk(), hologram);
        hologram.spawn();
        return hologram;
    }

}
