package io.github.pigaut.voxel.core.function.action.server;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;

public class ServerBroadcast implements ServerAction {

    private final EnhancedPlugin plugin;
    private final String message;

    public ServerBroadcast(EnhancedPlugin plugin, String message) {
        this.plugin = plugin;
        this.message = message;
    }

    @Override
    public void execute() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerState playerState = plugin.getPlayerState(player);
            playerState.sendMessage(message);
        }
    }

}
