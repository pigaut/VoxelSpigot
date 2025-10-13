package io.github.pigaut.voxel.core.function.action.server;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.core.function.action.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.amount.*;
import io.github.pigaut.yaml.configurator.load.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class DropItem implements Action {

    private final ItemStack item;
    private final Amount amount;
    private final boolean doFortune;
    private final Location location;


    public DropItem(ItemStack item, Amount amount, boolean doFortune, World world, double x, double y, double z) {
        this.item = item;
        this.amount = amount;
        this.doFortune = doFortune;
        this.location = new Location(world, x, y, z);
    }

    @Override
    public void execute(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        if (player == null || !doFortune) {
            ItemDrop.spawn(location, item, amount.getInteger());
            return;
        }

        int fortuneLevel = Fortune.getEnchantLevel(player.getInventory().getItemInMainHand());
        ItemDrop.spawn(location, item, amount.getInteger(), fortuneLevel);
    }

}
