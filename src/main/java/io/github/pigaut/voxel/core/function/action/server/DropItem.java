package io.github.pigaut.voxel.core.function.action.server;

import io.github.pigaut.voxel.server.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.load.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class DropItem implements ServerAction {

    private final ItemStack item;
    private final Location location;

    public DropItem(ItemStack item, World world, double x, double y, double z) {
        this.item = item;
        this.location = new Location(world, x, y, z);
    }

    @Override
    public void execute() {
        World world = location.getWorld();
        if (world == null) {
            world = SpigotServer.getDefaultWorld();
        }
        world.dropItemNaturally(location, item);
    }

}
