package io.github.pigaut.voxel.core.function.action.block;

import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class DropItemAtBlock implements BlockAction {

    private final ItemStack item;

    public DropItemAtBlock(ItemStack item) {
        this.item = item;
    }

    @Override
    public void execute(@NotNull Block block) {
        final Location location = block.getLocation().add(0.5, 0.5, 0.5);
        block.getWorld().dropItemNaturally(location, item);
    }

}
