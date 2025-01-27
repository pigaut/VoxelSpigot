package io.github.pigaut.voxel.function.condition.player;

import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.util.*;
import org.bukkit.enchantments.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.jetbrains.annotations.*;

public class PlayerToolHasEnchant implements PlayerCondition {

    private final Enchantment enchantment;
    private final Amount level;

    public PlayerToolHasEnchant(@NotNull Enchantment enchantment, @NotNull Amount level) {
        this.enchantment = enchantment;
        this.level = level;
    }

    @Override
    public boolean isMet(@NotNull PluginPlayer player) {
        final ItemStack item = player.getInventory().getItemInMainHand();
        if (item.hasItemMeta()) {
            final ItemMeta meta = item.getItemMeta();
            return meta.hasEnchant(enchantment) && level.match(meta.getEnchantLevel(enchantment));
        }
        return false;
    }

}
