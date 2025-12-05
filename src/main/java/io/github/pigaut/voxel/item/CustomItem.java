package io.github.pigaut.voxel.item;

import io.github.pigaut.voxel.bukkit.*;
import org.bukkit.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class CustomItem {

    private final NamespacedKey key;
    private final String name;
    private final ItemBuilder itemBuilder;

    private final EnumMap<InteractType, Runnable> interactActions = new EnumMap<>(InteractType.class);
    private final EnumMap<ClickType, Runnable> clickAction = new EnumMap<>(ClickType.class);

    public CustomItem(NamespacedKey key, String name, Material type) {
        this.key = key;
        this.name = name;
        this.itemBuilder = new ItemBuilder(type);
    }

    public @NotNull String getId() {
        return key + ":" + name;
    }

    public @NotNull NamespacedKey getKey() {
        return key;
    }

    public @NotNull String getName() {
        return name;
    }

    public ItemBuilder editor() {
        return itemBuilder;
    }

    public @NotNull ItemStack create() {
        ItemStack item = itemBuilder.build();

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            throw new IllegalStateException("Current item type doesn't support item meta.");
        }

        PersistentData.setString(meta, key, name);
        item.setItemMeta(meta);
        return item;
    }




}
