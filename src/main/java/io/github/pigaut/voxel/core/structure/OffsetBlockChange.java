package io.github.pigaut.voxel.core.structure;

import io.github.pigaut.voxel.bukkit.Rotation;
import org.bukkit.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

public abstract class OffsetBlockChange implements BlockChange {

    private final int offsetX;
    private final int offsetY;
    private final int offsetZ;

    protected OffsetBlockChange(int offsetX, int offsetY, int offsetZ) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }

    @Override
    public @NotNull Location getLocation(Location origin, io.github.pigaut.voxel.bukkit.Rotation rotation) {
        return rotation.apply(origin.clone(), offsetX, offsetY, offsetZ);
    }

    @Override
    public @NotNull Block getBlock(Location origin, Rotation rotation) {
        return getLocation(origin, rotation).getBlock();
    }

}
