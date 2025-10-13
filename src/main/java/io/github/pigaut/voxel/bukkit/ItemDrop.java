package io.github.pigaut.voxel.bukkit;

import io.github.pigaut.voxel.server.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

public class ItemDrop {

    private ItemDrop() {}

    public static void spawn(Location location, ItemStack item) {
        spawn(location, item, 1, 0);
    }

    public static void spawn(Location location, ItemStack item, int amount) {
        spawn(location, item, amount, 0);
    }

    public static void spawn(Location location, ItemStack item, int amount, int fortuneLevel) {
        World world = location.getWorld();
        if (world == null) {
            world = SpigotServer.getDefaultWorld();
        }

        ItemStack drop = item.clone();
        drop.setAmount(Fortune.getDropAmount(amount, fortuneLevel));
        world.dropItemNaturally(location, drop);
    }

}
