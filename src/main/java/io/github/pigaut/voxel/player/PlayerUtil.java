package io.github.pigaut.voxel.player;

import io.github.pigaut.voxel.placeholder.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

import java.util.*;

public class PlayerUtil {

    public static void giveItemsOrDrop(Player player, ItemStack... items) {
        final Map<Integer, ItemStack> leftoverItems = player.getInventory().addItem(items);
        if (!leftoverItems.isEmpty()) {
            final World world = player.getWorld();
            final Location playerLocation = player.getLocation();
            leftoverItems.values().forEach(item -> {
                world.dropItemNaturally(playerLocation, item);
            });
        }
    }

    public static void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                TextComponent.fromLegacyText(StringPlaceholders.parseAll(player, message)));
    }

}
