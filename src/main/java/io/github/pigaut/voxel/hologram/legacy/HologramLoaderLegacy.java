package io.github.pigaut.voxel.hologram.legacy;

import io.github.pigaut.voxel.hologram.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import io.github.pigaut.yaml.parser.*;
import org.jetbrains.annotations.*;

import java.util.*;

/*
 * Hologram Loader <1.19.4
 */
public class HologramLoaderLegacy implements ConfigLoader<Hologram> {

    private final EnhancedPlugin plugin;

    public HologramLoaderLegacy(EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getProblemDescription() {
        return "invalid hologram";
    }

    @Override
    public @NotNull Hologram loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        final String type = section.getString("type", StringStyle.CONSTANT);

        Hologram hologram;
        switch (type) {
            case "STATIC" -> {
                final String text = section.getOptionalString("text|title", StringColor.FORMATTER).orElse("none");
                final int update = section.getOptionalInteger("update").orElse(0);
                hologram = new StaticHologramLegacy(plugin, text, update);
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

                hologram = new AnimatedHologramLegacy(plugin, frames, update);
            }

            case "ITEM" -> {
                throw new InvalidConfigurationException(section, "type", "Item holograms require version 1.19.4 or newer");
            }

            case "BLOCK" -> {
                throw new InvalidConfigurationException(section, "type", "Block holograms require version 1.19.4 or newer");
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
        return new MultiLineHologram(sequence.getAll(Hologram.class));
    }

}
