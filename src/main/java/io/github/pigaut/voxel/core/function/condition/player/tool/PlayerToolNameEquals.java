package io.github.pigaut.voxel.core.function.condition.player.tool;

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
    public Boolean evaluate(@NotNull PlayerState player) {
        ItemStack tool = player.getInventory().getItemInMainHand();
        if (!tool.hasItemMeta()) {
            return false;
        }

        ItemMeta toolMeta = tool.getItemMeta();
        if (!toolMeta.hasDisplayName()) {
            return false;
        }

        String toolName = ChatColor.stripColor(toolMeta.getDisplayName());
        return toolName.equals(name);
    }

}
