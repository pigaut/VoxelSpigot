package io.github.pigaut.voxel.util;

import io.github.pigaut.yaml.amount.*;
import org.jetbrains.annotations.*;

public class BlockRange {

    public static final BlockRange ZERO = new BlockRange(Amount.ZERO, Amount.ZERO, Amount.ZERO);

    public final Amount rangeX;
    public final Amount rangeY;
    public final Amount rangeZ;

    public BlockRange(@NotNull Amount rangeX, @NotNull Amount rangeY, @NotNull Amount rangeZ) {
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        this.rangeZ = rangeZ;
    }

    public int getBlockX() {
        return rangeX.getInteger();
    }

    public double getX() {
        return rangeX.getDouble();
    }

    public int getBlockY() {
        return rangeY.getInteger();
    }

    public double getY() {
        return rangeY.getDouble();
    }

    public int getBlockZ() {
        return rangeZ.getInteger();
    }

    public double getZ() {
        return rangeZ.getDouble();
    }

}
