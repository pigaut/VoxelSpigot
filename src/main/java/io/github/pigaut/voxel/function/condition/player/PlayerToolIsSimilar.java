package io.github.pigaut.voxel.function.condition.player;

import io.github.pigaut.voxel.player.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class PlayerToolIsSimilar implements PlayerCondition {

    private final ItemStack item;

    public PlayerToolIsSimilar(ItemStack item) {
        this.item = item;
    }

    @Override
    public boolean isMet(@NotNull PluginPlayer player) {
        return item.isSimilar(player.getInventory().getItemInMainHand());
    }

}
