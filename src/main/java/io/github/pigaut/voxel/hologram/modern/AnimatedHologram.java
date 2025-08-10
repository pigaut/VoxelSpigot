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
import org.bukkit.scheduler.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class AnimatedHologram implements Hologram {

    private final EnhancedPlugin plugin;
    private final List<String> frames;
    private final TextDisplayOptions options;
    private final int update;
    private final int intervals;

    public AnimatedHologram(EnhancedPlugin plugin, List<String> frames, TextDisplayOptions options, int update) {
        this.plugin = plugin;
        this.frames = frames;
        this.options = options;
        this.update = update;
        this.intervals = frames.size() - 1;
        if (intervals < 1) {
            throw new IllegalArgumentException("Animated hologram needs at least two frames");
        }
    }

    @Override
    public @Nullable HologramDisplay spawn(Location location, Rotation rotation, PlaceholderSupplier... placeholders) {
        final World world = SpigotLibs.getWorldOrDefault(location);
        final Block block = world.getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (block.getType() != Material.AIR) {
            return null;
        }

        final HologramDisplay display = new GenericHologramDisplay(plugin, new Location(world, location.getX(), location.getY(), location.getZ())) {
            @Override
            public @NotNull Display create() {
                TextDisplay display = (TextDisplay) SpigotLibs.createEmptyTextDisplay(location, options, false);
                updateTask = new BukkitRunnable() {
                    int interval = 0;
                    @Override
                    public void run() {
                        if (!exists()) {
                            despawn();
                            return;
                        }
                        display.setText(StringPlaceholders.parseAll(frames.get(interval), placeholders));
                        interval = interval >= intervals ? 0 : interval + 1;
                    }
                }.runTaskTimer(plugin, 0, update);
                return display;
            }
        };

        plugin.getHolograms().registerHologram(location.getChunk(), display);
        display.spawn();
        return display;
    }

}
