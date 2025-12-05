package io.github.pigaut.voxel.listener;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.player.input.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class PlayerInputListener implements Listener {

    private final EnhancedPlugin plugin;

    public PlayerInputListener(EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        PlayerState player = plugin.getPlayerState(event.getPlayer());
        if (player.isAwaitingInput(InputSource.CHAT)) {
            event.setCancelled(true);
            plugin.getScheduler().runTask(() ->
                    player.submitInput(event.getMessage()));
        }
    }

}
