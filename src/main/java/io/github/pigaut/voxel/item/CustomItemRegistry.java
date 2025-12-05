package io.github.pigaut.voxel.item;

import io.github.pigaut.voxel.plugin.manager.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class CustomItemRegistry {

    private final Map<String, CustomItem> customItemsById = new HashMap<>();

    public @Nullable CustomItem getCustomItem(@NotNull String name) {
        return customItemsById.get(name);
    }

    public Collection<CustomItem> getCustomItems() {
        return new ArrayList<>(customItemsById.values());
    }

    public void registerItem(@NotNull CustomItem customItem) throws DuplicateElementException {
        String id = customItem.getId();
        if (customItemsById.containsKey(id)) {
            throw new DuplicateElementException(id);
        }
        customItemsById.put(customItem.getId(), customItem);
    }

    public void unregisterItem(@NotNull String id) {
        customItemsById.remove(id);
    }

}
