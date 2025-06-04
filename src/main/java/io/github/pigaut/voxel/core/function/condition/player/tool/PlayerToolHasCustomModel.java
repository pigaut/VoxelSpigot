package io.github.pigaut.voxel.core.function.condition.player.tool;

import io.github.pigaut.voxel.core.function.condition.player.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PlayerToolHasCustomModel implements PlayerCondition {

    private final List<Integer> validModels;

    public PlayerToolHasCustomModel(List<Integer> validModels) {
        this.validModels = validModels;
    }

    @Override
    public boolean isMet(@NotNull PlayerState player) {
        final ItemStack heldItem = player.getInventory().getItemInMainHand();
        if (!heldItem.hasItemMeta()) {
            return false;
        }
        final int toolModel = heldItem.getItemMeta().getCustomModelData();
        for (Integer validModel : validModels) {
            if (toolModel == validModel) {
                return true;
            }
        }
        return false;
    }

}

