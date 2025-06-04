package io.github.pigaut.voxel.core.item;

import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class Item implements Identifiable {

    private final String name;
    private final String group;
    private final ConfigField field;
    private final ItemStack itemStack;

    public Item(String name, @Nullable String group, ConfigField field, ItemStack itemStack) {
        this.name = name;
        this.group = group;
        this.field = field;
        this.itemStack = itemStack;
    }

    public @NotNull String getName() {
        return name;
    }

    public @Nullable String getGroup() {
        return group;
    }

    @Override
    public @NotNull ConfigField getField() {
        return field;
    }

    @Override
    public @NotNull ItemStack getIcon() {
        return itemStack.clone();
    }

    public ItemStack getItemStack() {
        return itemStack.clone();
    }

}
