package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.player.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class TakeItemFromPlayer implements PlayerAction {

    private final ItemStack item;

    public TakeItemFromPlayer(ItemStack item) {
        this.item = item;
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        player.getInventory().removeItem(item);
    }

}
