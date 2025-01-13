package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class ClearPlayerInventory implements PlayerAction {

    private final boolean removeArmor;

    public ClearPlayerInventory(boolean removeArmor) {
        this.removeArmor = removeArmor;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        final PlayerInventory inventory = player.getInventory();
        if (!removeArmor) {
            for (int i = 0; i < 36; i++) {
                inventory.setItem(i, null);
            }
        }
        else {
            inventory.clear();
        }
        player.updateInventory();
    }

}


