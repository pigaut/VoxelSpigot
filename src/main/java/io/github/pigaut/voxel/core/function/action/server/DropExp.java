package io.github.pigaut.voxel.core.function.action.server;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.util.*;
import org.bukkit.*;

public class DropExp implements ServerAction {

    private final Amount expAmount;
    private final Amount orbCount;
    private final Location location;

    public DropExp(Amount expAmount, Amount orbCount, Location location) {
        this.expAmount = expAmount;
        this.orbCount = orbCount;
        this.location = location;
    }

    @Override
    public void execute() {
        ExpOrb.spawn(location, expAmount, orbCount.getInt());
    }

}
