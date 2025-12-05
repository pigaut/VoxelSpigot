package io.github.pigaut.voxel.item;

import org.bukkit.*;
import org.bukkit.enchantments.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.jetbrains.annotations.*;

import java.util.*;

/**
 * A fluent builder for creating and modifying ItemStacks easily.
 */
public class ItemBuilder {

    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilder(@NotNull Material material) {
        this.item = new ItemStack(material);
        this.meta = this.item.getItemMeta();
    }

    public ItemBuilder(@NotNull ItemStack item) {
        this.item = item.clone();
        this.meta = this.item.getItemMeta();
    }

    public ItemBuilder withName(@Nullable String name) {
        if (name != null)
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        else
            meta.setDisplayName(null);
        return this;
    }

    public ItemBuilder withLore(@Nullable List<String> lore) {
        if (lore == null) {
            meta.setLore(null);
            return this;
        }

        List<String> colored = new ArrayList<>(lore.size());
        for (String line : lore) {
            colored.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        meta.setLore(colored);
        return this;
    }

    public ItemBuilder withLore(@NotNull String... lines) {
        return withLore(Arrays.asList(lines));
    }

    public ItemBuilder withAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder withEnchant(@NotNull Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder withEnchants(@NotNull Map<Enchantment, Integer> enchants) {
        enchants.forEach((ench, lvl) -> meta.addEnchant(ench, lvl, true));
        return this;
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder withFlags(@NotNull ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder withCustomModel(@Nullable Integer data) {
        meta.setCustomModelData(data);
        return this;
    }

    public @NotNull ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

}
