package io.github.pigaut.voxel.player;

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

}
