package io.github.pigaut.voxel.core.function.action.server;

import io.github.pigaut.voxel.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class StrikeLightning implements ServerAction {

    private final Location location;
    private final boolean doDamage;

    public StrikeLightning(World world, double x, double y, double z, boolean doDamage) {
        this.location = new Location(world, x, y, z);
        this.doDamage = doDamage;
    }

    @Override
    public void execute() {
        final World world = SpigotLibs.getWorldOrDefault(location);
        if (doDamage) {
            world.strikeLightning(location);
        } else {
            world.strikeLightningEffect(location);
        }
    }

}
