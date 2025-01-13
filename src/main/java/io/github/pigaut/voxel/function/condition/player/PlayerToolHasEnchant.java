package io.github.pigaut.voxel.function.condition.player;

import io.github.pigaut.voxel.player.*;
import org.bukkit.enchantments.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.jetbrains.annotations.*;

public class PlayerToolHasEnchant implements PlayerCondition {

    private final Enchantment enchantment;

    public PlayerToolHasEnchant(Enchantment enchantment) {
        this.enchantment = enchantment;
    }

    @Override
    public boolean isMet(@NotNull PluginPlayer player) {
        final ItemStack item = player.getInventory().getItemInMainHand();
        if (item.hasItemMeta()) {
            final ItemMeta meta = item.getItemMeta();
            return meta.hasEnchant(enchantment);
        }
        return false;
    }

}
