package io.github.pigaut.voxel.core.hologram;

import eu.decentsoftware.holograms.api.*;
import io.github.pigaut.voxel.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.bukkit.Rotation;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.scheduler.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class StaticHologram implements Hologram {

    private final EnhancedPlugin plugin;
    private final String text;
    private final int update;

    public StaticHologram(EnhancedPlugin plugin, String text) {
        this(plugin, text, 0);
    }

    public StaticHologram(EnhancedPlugin plugin, String text, int update) {
        this.plugin = plugin;
        this.text = text;
        this.update = update;
    }

    @Override
    public @Nullable HologramDisplay spawn(Location location, Rotation rotation, Collection<PlaceholderSupplier> placeholders) {
        World world = SpigotLibs.getWorldOrDefault(location);
        Block block = world.getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (block.getType() != Material.AIR) {
            return null;
        }

        HologramDisplay hologram = new DecentHologramDisplay(plugin, new Location(world, location.getX(), location.getY(), location.getZ())) {
            {
                final String parsedText = StringPlaceholders.parseAll(text, placeholders);
                DHAPI.addHologramLine(display, parsedText);
                display.showAll();

                if (update > 0) {
                    updateTask = new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (!exists()) {
                                cancel();
                                return;
                            }
                            final String parsedText = StringPlaceholders.parseAll(text, placeholders);
                            DHAPI.setHologramLine(display, 0, parsedText);
                        }
                    }.runTaskTimer(plugin, 0, update);
                }

            }
        };

        return hologram;
    }

}
