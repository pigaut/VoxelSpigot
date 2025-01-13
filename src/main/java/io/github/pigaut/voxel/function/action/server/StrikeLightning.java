package io.github.pigaut.voxel.function.action.server;

import org.bukkit.*;
import org.jetbrains.annotations.*;

public class StrikeLightning implements ServerAction {

    private final Location location;

    public StrikeLightning(@NotNull Location location) {
        this.location = location;
    }

    @Override
    public void execute() {
        final World world = location.getWorld();
        world.strikeLightning(location);
    }

}
