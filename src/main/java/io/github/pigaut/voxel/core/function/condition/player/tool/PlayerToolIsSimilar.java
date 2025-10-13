package io.github.pigaut.voxel.core.function.condition.player.tool;

import io.github.pigaut.voxel.core.function.condition.player.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PlayerToolIsSimilar implements PlayerCondition {

    private final List<ItemStack> items;

    public PlayerToolIsSimilar(List<ItemStack> items) {
        this.items = items;
    }

    @Override
    public Boolean evaluate(@NotNull PlayerState player) {
        final ItemStack tool = player.getInventory().getItemInMainHand();
        for (ItemStack item : items) {
            if (item.isSimilar(tool)) {
                return true;
            }
        }
        return false;
    }

}
