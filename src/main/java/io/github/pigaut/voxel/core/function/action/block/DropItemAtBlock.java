package io.github.pigaut.voxel.core.function.action.block;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.bukkit.ItemUtil;
import io.github.pigaut.voxel.core.function.action.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.amount.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.inventory.*;
import org.bukkit.util.*;
import org.jetbrains.annotations.*;

public class DropItemAtBlock implements Action {

    private final ItemStack item;
    private final Amount amount;
    private final boolean doFortune;

    public DropItemAtBlock(ItemStack item, Amount amount, boolean doFortune) {
        this.item = item;
        this.amount = amount;
        this.doFortune = doFortune;
    }

    @Override
    public void execute(@Nullable PlayerState player, @Nullable Event event, @Nullable Block block, @Nullable Entity target) {
        if (block == null) {
            return;
        }

        Location location = block.getLocation().add(0.5, 0.5, 0.5);
        if (player == null) {
            ItemUtil.dropItem(location.add(0, 0.5, 0), item, amount.getInteger());
            return;
        }

        Vector direction = player.getLocation().toVector().subtract(location.toVector()).normalize();
        Location offsetLocation = location.add(direction.multiply(0.5)).add(0, 0.2, 0);

        if (doFortune) {
            int fortuneLevel = Fortune.getEnchantLevel(player.getInventory().getItemInMainHand());
            ItemUtil.dropItem(offsetLocation, item, amount.getInteger(), fortuneLevel);
            return;
        }

        ItemUtil.dropItem(offsetLocation, item, amount.getInteger());
    }

}
