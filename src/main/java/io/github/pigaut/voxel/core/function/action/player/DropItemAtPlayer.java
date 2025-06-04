package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.player.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class DropItemAtPlayer implements PlayerAction {

    private final ItemStack item;

    public DropItemAtPlayer(ItemStack item) {
        this.item = item;
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        final World world = player.getWorld();
        world.dropItemNaturally(player.getLocation(), item);
    }

}
