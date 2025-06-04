package io.github.pigaut.voxel.player.input;

import io.github.pigaut.voxel.player.*;
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
        final PlayerState player = plugin.getPlayerState(event.getPlayer());
        if (player.isAwaitingInput(InputType.CHAT)) {
            event.setCancelled(true);

            final String input = event.getMessage();
            if (input.equals("ESC")) {
                player.setAwaitingInput(null);
                return;
            }

            player.setLastInput(input);
        }
    }

}
