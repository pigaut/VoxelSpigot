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
    public @Nullable HologramDisplay spawn(Location location, Rotation rotation, Collection<PlaceholderSupplier> placeholders) {
        World world = SpigotLibs.getWorldOrDefault(location);
        Block block = world.getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (block.getType() != Material.AIR) {
            return null;
        }

        HologramDisplay spawnedHologram = new DecentHologramDisplay(plugin, new Location(world, location.getX(), location.getY(), location.getZ())) {
            {
                final String parsedText = StringPlaceholders.parseAll(frames.get(0), placeholders);
                DHAPI.addHologramLine(display, 0, parsedText);
                display.showAll();

                updateTask = new BukkitRunnable() {
                    int interval = 1;
                    @Override
                    public void run() {
                        if (!exists()) {
                            cancel();
                            return;
                        }
                        final String parsedText = StringPlaceholders.parseAll(frames.get(interval), placeholders);
                        DHAPI.setHologramLine(display, 0, parsedText);
                        interval = interval >= intervals ? 0 : interval + 1;
                    }
                }.runTaskTimer(plugin, update, update);
            }
        };

        return spawnedHologram;
    }

}
