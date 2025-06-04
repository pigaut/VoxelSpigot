package io.github.pigaut.voxel.core.item;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.section.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.io.*;

public class ItemManager extends ManagerContainer<Item> {

    public ItemManager(EnhancedJavaPlugin plugin) {
        super(plugin);
    }

    public @Nullable ItemStack getItemStack(@NotNull String name) {
        final Item item = this.get(name);
        return item != null ? item.getItemStack() : null;
    }

    @Override
    public @Nullable String getFilesDirectory() {
        return "items";
    }

    @Override
    public void loadFile(@NotNull File file) {
        final RootSection config = new RootSection(file, plugin.getConfigurator());
        config.setPrefix("Item");
        config.load();

        for (String itemName : config.getKeys()) {
            final String itemGroup = PathGroup.byItemFile(file);
            final ConfigField itemField = config.getField(itemName);
            final ItemStack itemStack = itemField.load(ItemStack.class);
            this.add(new Item(itemName, itemGroup, itemField, itemStack));
        }
    }

}
