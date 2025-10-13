package io.github.pigaut.voxel.core.message.impl;

import io.github.pigaut.voxel.core.hologram.*;
import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.menu.button.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.concurrent.*;

public class HologramMessage extends GenericMessage {

    private final EnhancedPlugin plugin;
    private final @Nullable Hologram hologram;
    private final int duration;
    private final Double radiusX, radiusY, radiusZ;

    public HologramMessage(@NotNull EnhancedPlugin plugin, @Nullable Hologram hologram, int duration,
                           @Nullable Double radiusX, @Nullable Double radiusY, @Nullable Double radiusZ) {
        this(plugin, UUID.randomUUID().toString(), null, hologram, duration, radiusX, radiusY, radiusZ);
    }

    public HologramMessage(@NotNull EnhancedPlugin plugin, String name, @Nullable String group,
                           @Nullable Hologram hologram, int duration, @Nullable Double radiusX,
                           @Nullable Double radiusY, @Nullable Double radiusZ) {
        super(name, group);
        this.plugin = plugin;
        this.hologram = hologram;
        this.duration = duration;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        this.radiusZ = radiusZ;
    }

    @Override
    public @NotNull MessageType getType() {
        return MessageType.HOLOGRAM;
    }

    @Override
    public @NotNull ItemStack getIcon() {
        return IconBuilder.of(Material.BEACON).buildIcon();
    }

    @Override
    public void send(@NotNull Player player, PlaceholderSupplier... placeholderSuppliers) {
        if (hologram == null) {
            player.sendMessage(ChatColor.RED + "DecentHolograms needs to be installed to use holograms.");
            return;
        }

        final Location location = player.getLocation();
        location.add(player.getFacing().getDirection().multiply(2));

        if (radiusX != null && radiusX > 0) {
            location.add(ThreadLocalRandom.current().nextDouble(-radiusX, radiusX), 0, 0);
        }

        if (radiusY != null && radiusY > 0) {
            location.add(0, ThreadLocalRandom.current().nextDouble(-radiusY, radiusY), 0);
        }

        if (radiusZ != null && radiusZ > 0) {
            location.add(0, 0, ThreadLocalRandom.current().nextDouble(-radiusZ, radiusZ));
        }

        final HologramDisplay display = hologram.spawn(location, placeholderSuppliers);

        if (display != null) {
            plugin.getScheduler().runTaskLater(duration, display::destroy);
        }
    }

}
