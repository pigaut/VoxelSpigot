package io.github.pigaut.voxel.core.function.action.block;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.yaml.amount.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

public class DropExpAtBlock implements BlockAction {

    private final Amount expAmount;
    private final @Nullable Amount orbCount;

    public DropExpAtBlock(Amount expAmount, @Nullable Amount orbCount) {
        this.expAmount = expAmount;
        this.orbCount = orbCount;
    }

    @Override
    public void execute(@NotNull Block block) {
        Location location = block.getLocation().add(0.5, 0.5, 0.5);
        if (orbCount == null) {
            Exp.drop(location, expAmount.getInteger());
            return;
        }
        Exp.drop(location, expAmount, orbCount.getInteger());
    }

}
