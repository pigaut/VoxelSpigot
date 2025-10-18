package io.github.pigaut.voxel.core.function.action.server;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.yaml.amount.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class DropExp implements ServerAction {

    private final Amount expAmount;
    private final @Nullable Amount orbCount;
    private final Location location;

    public DropExp(Amount expAmount, @Nullable Amount orbCount, World world, double x, double y, double z) {
        this.expAmount = expAmount;
        this.orbCount = orbCount;
        this.location = new Location(world, x, y, z);
    }

    @Override
    public void execute() {
        if (orbCount != null) {
            Exp.drop(location, expAmount, orbCount.getInteger());
        }
        else {
            Exp.drop(location, expAmount.getInteger());
        }
    }

}
