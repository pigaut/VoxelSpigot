package io.github.pigaut.voxel.function.action.block;

import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class DropItemOnBlock implements BlockAction {

    private final ItemStack item;

    public DropItemOnBlock(ItemStack item) {
        this.item = item;
    }

    @Override
    public void execute(@NotNull Block block) {
        final Location location = block.getLocation();
        block.getWorld().dropItemNaturally(location, item);
    }

}
