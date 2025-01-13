package io.github.pigaut.voxel.function.action.player;

import io.github.pigaut.voxel.player.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class DropItemOnPlayer implements PlayerAction {

    private final ItemStack item;

    public DropItemOnPlayer(ItemStack item) {
        this.item = item;
    }

    @Override
    public void execute(@NotNull PluginPlayer player) {
        final World world = player.getWorld();
        world.dropItemNaturally(player.getLocation(), item);
    }

}
