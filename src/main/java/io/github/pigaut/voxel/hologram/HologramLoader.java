package io.github.pigaut.voxel.hologram;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.load.*;
import io.github.pigaut.yaml.convert.format.*;
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

        final String type = section.getString("type", CaseStyle.CONSTANT).throwOrElse("STATIC");

        Hologram hologram;
        switch (type) {
            case "STATIC" -> {
                final String text = section.getString("text|title", StringColor.FORMATTER).throwOrElse("none");
                final int update = section.getInteger("update").throwOrElse(0);
                hologram = new StaticHologram(plugin, text, update);
            }

            case "ANIMATED" -> {
                final List<String> frames = new ArrayList<>();
                frames.addAll(section.getStringList("frames", StringColor.FORMATTER));
                while (frames.size() < 2) {
                    frames.add("none");
                }

                final int update = section.getInteger("update").throwOrElse(3);
                if (update < 1) {
                    throw new InvalidConfigurationException(section, "update", "Animation update interval must be at least 1 tick");
                }

                hologram = new AnimatedHologram(plugin, frames, update);
            }

            case "ITEM_DISPLAY" -> {
                final ItemStack item = section.get("item", ItemStack.class).throwOrElse(new ItemStack(Material.GRASS_BLOCK));
                hologram = new ItemHologram(plugin, item);
            }

            case "BLOCK_DISPLAY" -> {
                final Material material = section.get("block", Material.class).throwOrElse(Material.DIRT);
                if (!material.isBlock()) {
                    throw new InvalidConfigurationException(section, "block", "Material is not a block");
                }
                hologram = new BlockHologram(plugin, material);
            }

            default -> {
                throw new InvalidConfigurationException(section, "type", "Found unknown hologram type: '" + type + "'");
            }
        }

        final double offsetX = section.getDouble("offset.x").throwOrElse(0d);
        final double offsetY = section.getDouble("offset.y").orElse(0d);
        final double offsetZ = section.getDouble("offset.z").orElse(0d);
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
