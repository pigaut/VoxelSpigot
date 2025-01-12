package io.github.pigaut.voxel.item;

import io.github.pigaut.voxel.plugin.manager.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ItemManager extends Manager {

    private final Map<String, ItemStack> itemsByName = new HashMap<>();

    public void addItemStack(String name, ItemStack item) {
        itemsByName.put(name, item);
    }

    public void removeItemStack(String name) {
        itemsByName.remove(name);
    }

    public @Nullable ItemStack getItemStack(String name) {
        final ItemStack item = itemsByName.get(name);
        return item != null ? item.clone() : null;
    }

    public List<String> getItemNames() {
        return new ArrayList<>(itemsByName.keySet());
    }

    public Map<String, ItemStack> getAllItems() {
        return new HashMap<>(itemsByName);
    }

    public void clearItems() {
        itemsByName.clear();
    }

}
