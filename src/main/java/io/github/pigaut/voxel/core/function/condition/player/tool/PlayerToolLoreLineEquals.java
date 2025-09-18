package io.github.pigaut.voxel.core.function.condition.player.tool;

import io.github.pigaut.voxel.core.function.condition.player.*;
import io.github.pigaut.voxel.player.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PlayerToolLoreLineEquals implements PlayerCondition {

    private final String lore;
    private final int line;

    public PlayerToolLoreLineEquals(String lore, int line) {
        this.lore = lore;
        this.line = line;
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
        if (line >= lore.size()) {
            return false;
        }

        final String loreLine = ChatColor.stripColor(lore.get(line));
        return loreLine.equals(this.lore);
    }

}
