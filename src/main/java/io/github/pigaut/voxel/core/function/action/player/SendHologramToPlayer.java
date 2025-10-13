package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.core.hologram.*;
import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.core.message.impl.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class SendHologramToPlayer implements PlayerAction {

    private final Message hologram;

    public SendHologramToPlayer(EnhancedPlugin plugin, String title, int duration, Double radiusX, Double radiusY, Double radiusZ) {
        this.hologram = new HologramMessage(plugin, new StaticHologram(plugin, title), duration, radiusX, radiusY, radiusZ);
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        player.sendMessage(hologram);
    }

}
