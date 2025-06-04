package io.github.pigaut.voxel.core.function.condition.player.tool;

import io.github.pigaut.voxel.core.function.condition.player.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PlayerToolTypeIsEqual implements PlayerCondition {

    private final List<Material> materials;

    public PlayerToolTypeIsEqual(List<Material> materials) {
        this.materials = materials;
    }

    @Override
    public boolean isMet(@NotNull PlayerState player) {
        final Material toolMaterial = player.getInventory().getItemInMainHand().getType();
        for (Material material : materials) {
            if (material == toolMaterial) {
                return true;
            }
        }
        return false;
    }

}
