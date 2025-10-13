package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.yaml.amount.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class DropExpAtPlayer implements PlayerAction {

    private final Amount expAmount;
    private final @Nullable Amount orbCount;

    public DropExpAtPlayer(Amount expAmount, @Nullable Amount orbCount) {
        this.expAmount = expAmount;
        this.orbCount = orbCount;
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        Location location = player.getLocation();
        if (orbCount == null) {
            ExpDrop.spawn(location, expAmount.getInteger());
            return;
        }
        ExpDrop.spawn(location, expAmount, orbCount.getInteger());
    }

}
