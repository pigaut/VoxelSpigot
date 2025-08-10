package io.github.pigaut.voxel.hologram;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import io.github.pigaut.yaml.parser.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class HologramLoader implements ConfigLoader<Hologram> {

    private final EnhancedPlugin plugin;

    public HologramLoader(EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getProblemDescription() {
        return "invalid hologram";
    }

    @Override
    public @NotNull Hologram loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        if (!SpigotServer.isPluginEnabled("DecentHolograms")) {
            throw new InvalidConfigurationException(section, "Holograms require DecentHolograms installed (https://www.spigotmc.org/resources/decentholograms.96927/)");
        }

        final String type = section.getString("type", StringStyle.CONSTANT);

        Hologram hologram;
        switch (type) {
            case "STATIC" -> {
                final String text = section.getOptionalString("text|title", StringColor.FORMATTER).orElse("none");
                final int update = section.getOptionalInteger("update").orElse(0);
                hologram = new StaticHologram(plugin, text, update);
            }

            case "ANIMATED" -> {
                final List<String> frames = new ArrayList<>();
                frames.addAll(section.getStringList("frames", StringColor.FORMATTER));
                while (frames.size() < 2) {
                    frames.add("none");
                }

                final int update = section.getOptionalInteger("update").orElse(3);
                if (update < 1) {
                    throw new InvalidConfigurationException(section, "update", "Animation update interval must be at least 1 tick");
                }

                hologram = new AnimatedHologram(plugin, frames, update);
            }

            case "ITEM_DISPLAY" -> {
                final ItemStack item = section.getOptional("item", ItemStack.class).orElse(new ItemStack(Material.GRASS_BLOCK));
                hologram = new ItemHologram(plugin, item);
            }

            case "BLOCK_DISPLAY" -> {
                final Material material = section.getOptional("block", Material.class).orElse(Material.DIRT);
                if (!material.isBlock()) {
                    throw new InvalidConfigurationException(section, "block", "Material is not a block");
                }
                hologram = new BlockHologram(plugin, material);
            }

            default -> {
                throw new InvalidConfigurationException(section, "type", "Found invalid hologram type: '" + type + "'");
            }

        }

        final double offsetX = section.getOptionalDouble("offset.x").orElse(0d);
        final double offsetY = section.getOptionalDouble("offset.y").orElse(0d);
        final double offsetZ = section.getOptionalDouble("offset.z").orElse(0d);
        if (offsetX != 0 || offsetY != 0 || offsetZ != 0) {
            return new OffsetHologram(hologram, offsetX, offsetY, offsetZ);
        }

        return hologram;
    }

    @Override
    public @NotNull Hologram loadFromSequence(@NotNull ConfigSequence sequence) throws InvalidConfigurationException {
        if (!SpigotServer.isPluginEnabled("DecentHolograms")) {
            throw new InvalidConfigurationException(sequence, "Holograms require DecentHolograms installed (https://www.spigotmc.org/resources/decentholograms.96927/)");
        }
        return new MultiLineHologram(sequence.getAll(Hologram.class));
    }

}
