package io.github.pigaut.voxel.core.item;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class ItemManager extends ConfigBackedManager.SectionKey<Item> {

    public ItemManager(EnhancedJavaPlugin plugin) {
        super(plugin, "items");
    }

    public @Nullable ItemStack getItemStack(@NotNull String name) {
        final Item item = this.get(name);
        return item != null ? item.getItemStack() : null;
    }

    @Override
    public void loadFromSectionKey(ConfigSection section, String key) throws InvalidConfigurationException {
        final String itemGroup = Group.byItemFile(section.getRoot().getFile());
        final ItemStack itemStack = section.getRequired(key, ItemStack.class);
        try {
            add(new Item(key, itemGroup, itemStack));
        }
        catch (DuplicateElementException e) {
            throw new InvalidConfigurationException(section, key, e.getMessage());
        }
    }

}
