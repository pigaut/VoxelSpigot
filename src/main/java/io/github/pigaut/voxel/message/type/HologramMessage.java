package io.github.pigaut.voxel.message.type;

import io.github.pigaut.voxel.hologram.*;
import io.github.pigaut.voxel.hologram.display.*;
import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.entity.*;

public class HologramMessage implements Message {

    private final EnhancedPlugin plugin;
    private final Hologram hologram;
    private final int duration;

    public HologramMessage(EnhancedPlugin plugin, Hologram hologram, int duration) {
        this.plugin = plugin;
        this.hologram = hologram;
        this.duration = duration;
    }

    @Override
    public void send(Player player, PlaceholderSupplier... placeholderSuppliers) {
        final HologramDisplay display = hologram.spawn(player.getLocation().add(player.getFacing().getDirection().multiply(2)),
                false, placeholderSuppliers);

        if (display != null) {
            plugin.getScheduler().runTaskLater(duration, display::despawn);
        }
    }

}
