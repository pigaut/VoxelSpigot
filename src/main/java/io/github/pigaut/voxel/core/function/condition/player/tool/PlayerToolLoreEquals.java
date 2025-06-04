package io.github.pigaut.voxel.core.function.condition.player.tool;

import io.github.pigaut.voxel.core.function.condition.player.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PlayerToolLoreEquals implements PlayerCondition {

    private final List<String> lore;

    public PlayerToolLoreEquals(List<String> lore) {
        this.lore = lore;
    }

    @Override
    public boolean isMet(@NotNull PlayerState player) {
        final ItemStack tool = player.getInventory().getItemInMainHand();
        if (!tool.hasItemMeta()) {
            return false;
        }

        final ItemMeta toolMeta = tool.getItemMeta();
        if (!toolMeta.hasLore()) {
            return false;
        }

        final List<String> lore = toolMeta.getLore();
        if (this.lore.size() != lore.size()) {
            return false;
        }

        for (int i = 0; i < lore.size(); i++) {
            final String loreLine = this.lore.get(i);
            final String loreLineToCheck = ChatColor.stripColor(lore.get(i));
            if (!loreLine.equals(loreLineToCheck)) {
                return false;
            }
        }
        return true;
    }

}
