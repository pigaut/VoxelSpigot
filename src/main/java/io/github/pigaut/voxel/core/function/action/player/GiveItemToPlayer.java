package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.player.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class GiveItemToPlayer implements PlayerAction {

    private final ItemStack item;

    public GiveItemToPlayer(ItemStack item) {
        this.item = item;
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        player.giveItemOrDrop(item);
    }

}
