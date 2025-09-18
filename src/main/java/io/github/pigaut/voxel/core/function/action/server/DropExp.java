package io.github.pigaut.voxel.core.function.action.server;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.yaml.amount.*;
import org.bukkit.*;

public class DropExp implements ServerAction {

    private final Amount expAmount;
    private final Amount orbCount;
    private final Location location;

    public DropExp(Amount expAmount, World world, double x, double y, double z, Amount orbCount) {
        this.expAmount = expAmount;
        this.orbCount = orbCount;
        this.location = new Location(world, x, y, z);
    }

    @Override
    public void execute() {
        ExpOrb.spawn(location, expAmount, orbCount.getInteger());
    }

}
