package io.github.pigaut.voxel.core.function.action.server;

import io.github.pigaut.voxel.server.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class DropItem implements ServerAction {

    private final ItemStack item;
    private final Location location;

    public DropItem(ItemStack item, Location location) {
        this.item = item;
        this.location = location;
    }

    @Override
    public void execute() {
        World world = location.getWorld();
        if (world == null) {
            world = SpigotServer.getDefaultWorld();
        }
        world.dropItemNaturally(location, item);
    }

    public static ConfigLoader<DropItem> newConfigLoader() {
        return new BranchLoader<>() {
            @Override
            public @NotNull DropItem loadFromBranch(ConfigBranch branch) throws InvalidConfigurationException {
                final ItemStack item = branch.getField("item|value", 1).load(ItemStack.class);
                final Location location = branch.getField("location", 2).load(Location.class);
                return new DropItem(item, location);
            }
        };
    }

}
