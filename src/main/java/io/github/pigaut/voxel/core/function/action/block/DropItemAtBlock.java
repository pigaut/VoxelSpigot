package io.github.pigaut.voxel.core.function.action.block;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.core.function.action.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.amount.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.inventory.*;
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

        Location location = block.getLocation().add(0.5, 1, 0.5);
        if (player == null || !doFortune) {
            ItemDrop.spawn(location, item, amount.getInteger());
            return;
        }

        int fortuneLevel = Fortune.getEnchantLevel(player.getInventory().getItemInMainHand());
        ItemDrop.spawn(location, item, amount.getInteger(), fortuneLevel);
    }

}
