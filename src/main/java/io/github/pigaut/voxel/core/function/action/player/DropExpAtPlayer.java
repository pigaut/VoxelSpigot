package io.github.pigaut.voxel.core.function.action.player;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.util.*;
import org.jetbrains.annotations.*;

public class DropExpAtPlayer implements PlayerAction {

    private final Amount expAmount;
    private final Amount orbCount;

    public DropExpAtPlayer(Amount expAmount, Amount orbCount) {
        this.expAmount = expAmount;
        this.orbCount = orbCount;
    }

    @Override
    public void execute(@NotNull PlayerState player) {
        ExpOrb.spawn(player.getLocation(), expAmount, orbCount.getInt());
    }

}
