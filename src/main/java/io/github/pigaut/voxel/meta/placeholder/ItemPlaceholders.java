package io.github.pigaut.voxel.meta.placeholder;

import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ItemPlaceholders {

    public static ItemStack parseAll(@NotNull ItemStack item, @NotNull PlaceholderSupplier... placeholderSuppliers) {
        return parseAll(null, item, placeholderSuppliers);
    }

    public static ItemStack parseAll(@Nullable OfflinePlayer player, @NotNull ItemStack item) {
        return parseAll(player, item, PlaceholderSupplier.EMPTY);
    }

    public static ItemStack parseAll(@Nullable OfflinePlayer player, @NotNull ItemStack item,
                                     @NotNull PlaceholderSupplier... placeholderSuppliers) {
        if (item.hasItemMeta()) {
            final ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(StringPlaceholders.parseAll(player, meta.getDisplayName(), placeholderSuppliers));

            final List<String> lore = meta.getLore();
            for (int i = 0; i < lore.size(); i++) {
                lore.set(i, StringPlaceholders.parseAll(player, lore.get(i), placeholderSuppliers));
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }


}
