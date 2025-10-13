package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.amount.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class GiveItemToPlayer implements PlayerAction {

    private final ItemStack item;
    private final Amount amount;
    private final boolean doFortune;

    public GiveItemToPlayer(ItemStack item, Amount amount, boolean doFortune) {
        this.item = item;
        this.amount = amount;
        this.doFortune = doFortune;
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        ItemStack drop = item.clone();
        int baseAmount = amount.getInteger();

        if (doFortune) {
            int fortuneLevel = Fortune.getEnchantLevel(player.getInventory().getItemInMainHand());
            drop.setAmount(Fortune.getDropAmount(baseAmount, fortuneLevel));
            player.giveItemOrDrop(drop);
        }

        else {
            drop.setAmount(baseAmount);
            player.giveItemOrDrop(drop);
        }
    }

}
