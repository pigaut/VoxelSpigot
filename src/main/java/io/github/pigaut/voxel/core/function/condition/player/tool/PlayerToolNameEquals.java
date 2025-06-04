package io.github.pigaut.voxel.core.function.condition.player.tool;

import io.github.pigaut.voxel.core.function.action.player.*;
import io.github.pigaut.voxel.core.function.condition.player.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.jetbrains.annotations.*;

public class PlayerToolNameEquals implements PlayerCondition {

    private final String name;

    public PlayerToolNameEquals(String name) {
        this.name = name;
    }

    @Override
    public boolean isMet(@NotNull PlayerState player) {
        final ItemStack tool = player.getInventory().getItemInMainHand();
        if (!tool.hasItemMeta()) {
            return false;
        }

        final ItemMeta toolMeta = tool.getItemMeta();
        if (!toolMeta.hasDisplayName()) {
            return false;
        }

        final String toolName = ChatColor.stripColor(toolMeta.getDisplayName());
        return toolName.equals(name);
    }

}
