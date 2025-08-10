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

public class StaticHologram implements Hologram {

    private final EnhancedPlugin plugin;
    private final String text;
    private final TextDisplayOptions options;
    private final int update;

    public StaticHologram(EnhancedPlugin plugin, String text, TextDisplayOptions options, int update) {
        this.plugin = plugin;
        this.text = text;
        this.options = options;
        this.update = update;
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
                final String parsedText = StringPlaceholders.parseAll(text, placeholders);
                final TextDisplay display = (TextDisplay) SpigotLibs.createTextDisplay(location, parsedText, options, false);

                if (update > 0) {
                    updateTask = new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (!exists()) {
                                despawn();
                                return;
                            }
                            display.setText(StringPlaceholders.parseAll(text, placeholders));
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
