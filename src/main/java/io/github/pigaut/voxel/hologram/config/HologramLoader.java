package io.github.pigaut.voxel.hologram.config;

import io.github.pigaut.voxel.hologram.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
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
    public @NotNull Hologram loadFromSection(@NotNull ConfigSection config) throws InvalidConfigurationException {
        final boolean animated = config.contains("frames");
        final Hologram hologram;
        if (animated) {
            final List<String> frames = config.getStringList("frames", StringColor.FORMATTER);
            while (frames.size() < 2) {
                frames.add("none");
            }
            final int update = config.getOptionalInteger("update").orElse(3);
            if (update < 1) {
                throw new InvalidConfigurationException(config, "update", "Animation update interval must be at least 1 tick");
            }
            hologram = new AnimatedHologram(plugin, frames, update);
        }
        else {
            final String text = config.getOptionalString("text", StringColor.FORMATTER).orElse("none");
            final int update = config.getOptionalInteger("update").orElse(0);
            hologram = new SimpleHologram(plugin, text, update);
        }

        final double offsetX = config.getOptionalDouble("offset.x").orElse(0d);
        final double offsetY = config.getOptionalDouble("offset.y").orElse(0d);
        final double offsetZ = config.getOptionalDouble("offset.z").orElse(0d);
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
