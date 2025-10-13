package io.github.pigaut.voxel.core.structure;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;

public class StructureWandListener implements Listener {

    private final EnhancedPlugin plugin;

    public StructureWandListener(EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (!event.hasBlock() || !event.hasItem() || event.getHand() != EquipmentSlot.HAND
                || !plugin.getOptions().isStructureWand(event.getItem())) {
            return;
        }
        event.setCancelled(true);

        final Player player = event.getPlayer();
        if (!player.hasPermission(plugin.getPermission("structure.wand"))) {
            plugin.sendMessage(player, "missing-wand-permission");
            return;
        }

        final PlayerState playerState = plugin.getPlayerState(player.getUniqueId());
        final Location targetLocation = event.getClickedBlock().getLocation();
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            playerState.setFirstSelection(targetLocation);
            plugin.sendMessage(player, "selected-first-position");
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            playerState.setSecondSelection(targetLocation);
            plugin.sendMessage(player, "selected-second-position");
        }
    }

}
