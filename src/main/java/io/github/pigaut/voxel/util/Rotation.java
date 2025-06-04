package io.github.pigaut.voxel.util;

import org.bukkit.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.stream.*;

public enum Rotation {

    NONE {
        @Override
        public Location apply(Location location, double x, double y, double z) {
            return location.add(x, y, z);
        }

        @Override
        public BlockFace translateBlockFace(@NotNull BlockFace blockFace) {
            return blockFace;
        }

        @Override
        public Axis translateAxis(Axis axis) {
            return axis;
        }
    },
    RIGHT {
        @Override
        public Location apply(Location location, double x, double y, double z) {
            return location.add(-z, y, x);
        }

        @Override
        public Axis translateAxis(Axis axis) {
            if (axis == Axis.Y) {
                return Axis.Y;
            }
            return axis == Axis.X ? Axis.Z : Axis.X;
        }
    },
    BACK {
        @Override
        public Location apply(Location location, double x, double y, double z) {
            return location.add(-x, y, -z);
        }

        @Override
        public BlockFace translateBlockFace(@NotNull BlockFace blockFace) {
            return blockFace.getOppositeFace();
        }

        @Override
        public Axis translateAxis(Axis axis) {
            return axis;
        }
    },
    LEFT {
        @Override
        public Location apply(Location location, double x, double y, double z) {
            return location.add(z, y, -x);
        }

        @Override
        public Axis translateAxis(Axis axis) {
            if (axis == Axis.Y) {
                return Axis.Y;
            }
            return axis == Axis.X ? Axis.Z : Axis.X;
        }
    };

    public abstract Location apply(Location location, double x, double y, double z);

    public BlockFace translateBlockFace(@NotNull BlockFace blockFace) {
        return rotateBlockFace(blockFace, this);
    }

    public Set<BlockFace> translateBlockFaces(@NotNull Collection<@NotNull BlockFace> blockFaces) {
        return blockFaces.stream()
                .map(this::translateBlockFace)
                .collect(Collectors.toSet());
    }

    public abstract Axis translateAxis(Axis axis);

    public Rotation next() {
        if (this == RIGHT) return BACK;
        if (this == BACK) return LEFT;
        if (this == LEFT) return NONE;
        return RIGHT;
    }

    private static final BlockFace[] ORDER = {
            BlockFace.NORTH, BlockFace.NORTH_NORTH_EAST, BlockFace.NORTH_EAST, BlockFace.EAST_NORTH_EAST,
            BlockFace.EAST, BlockFace.EAST_SOUTH_EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH_SOUTH_EAST,
            BlockFace.SOUTH, BlockFace.SOUTH_SOUTH_WEST, BlockFace.SOUTH_WEST, BlockFace.WEST_SOUTH_WEST,
            BlockFace.WEST, BlockFace.WEST_NORTH_WEST, BlockFace.NORTH_WEST, BlockFace.NORTH_NORTH_WEST
    };

    public static BlockFace rotateBlockFace(BlockFace face, Rotation rotation) {
        int index = -1;
        for (int i = 0; i < ORDER.length; i++) {
            if (ORDER[i] == face) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return face;
        }

        int stepOffset = switch (rotation) {
            case NONE -> 0;
            case RIGHT -> 4;
            case BACK -> 8;
            case LEFT -> 12;
        };

        return ORDER[(index + stepOffset) % ORDER.length];
    }

}
