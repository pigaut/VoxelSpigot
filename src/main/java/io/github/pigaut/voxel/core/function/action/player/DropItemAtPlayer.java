package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.amount.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class DropItemAtPlayer implements PlayerAction {

    private final ItemStack item;
    private final Amount amount;
    private final boolean doFortune;

    public DropItemAtPlayer(ItemStack item, Amount amount, boolean doFortune) {
        this.item = item;
        this.amount = amount;
        this.doFortune = doFortune;
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        Location location = player.getLocation();
        if (doFortune) {
            int fortuneLevel = Fortune.getEnchantLevel(player.getInventory().getItemInMainHand());
            ItemDrop.spawn(location, item, amount.getInteger(), fortuneLevel);
            return;
        }
        ItemDrop.spawn(location, item, amount.getInteger());
    }

}
