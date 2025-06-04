package io.github.pigaut.voxel.core.function.condition.player;

import io.github.pigaut.voxel.player.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class PlayerHasItem implements PlayerCondition {

    private final ItemStack item;

    public PlayerHasItem(ItemStack item) {
        this.item = item;
    }

    @Override
    public boolean isMet(@NotNull PlayerState player) {
        return player.getInventory().containsAtLeast(item, item.getAmount());
    }

}
