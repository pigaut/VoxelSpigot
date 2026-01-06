package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.core.function.action.*;
import io.github.pigaut.voxel.core.hologram.*;
import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.core.message.impl.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.jetbrains.annotations.*;

public class SendHologramToPlayer implements PlayerAction {

    private final Message hologram;

    public SendHologramToPlayer(EnhancedPlugin plugin, String title, int duration,
                                Double offsetX, Double offsetY, Double offsetZ,
                                Double radiusX, Double radiusY, Double radiusZ) {
        Hologram hologram = new StaticHologram(plugin, title);
        hologram = new OffsetHologram(hologram, offsetX, offsetY, offsetZ);
        this.hologram = new HologramMessage(plugin, hologram, duration, radiusX, radiusY, radiusZ);
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        hologram.send(player);
    }

    public static class NotEnabled implements PlayerAction {
        @Override
        public void execute(@NotNull PlayerState player) {
            player.sendMessage(ChatColor.RED + "You need DecentHolograms installed to use holograms.");
        }
    }

}
