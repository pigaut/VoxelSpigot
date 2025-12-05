package io.github.pigaut.voxel.listener;

import io.github.pigaut.voxel.plugin.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class PlayerJoinQuitListener implements Listener {

    private final EnhancedPlugin plugin;

    public PlayerJoinQuitListener(EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        plugin.getPlayersState().registerPlayer(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        plugin.getPlayersState().unregisterPlayer(event.getPlayer());
    }

}
