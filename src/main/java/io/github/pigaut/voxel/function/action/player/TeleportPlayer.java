package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class TeleportPlayer implements PlayerAction {

    private final Location destination;

    public TeleportPlayer(Location destination) {
        this.destination = destination;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        player.teleport(destination);
    }

}
