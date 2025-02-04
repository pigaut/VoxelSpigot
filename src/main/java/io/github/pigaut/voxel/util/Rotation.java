package io.github.pigaut.voxel.util;

import org.bukkit.*;
import org.bukkit.block.*;
import org.jetbrains.annotations.*;

public enum Rotation {

    NONE {
        @Override
        public Location apply(Location location, double x, double y, double z) {
            return location.add(x, y, z);
        }

        @Override
        public BlockFace translateBlockFace(BlockFace blockFace) {
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
        public BlockFace translateBlockFace(BlockFace blockFace) {
            if (blockFace == BlockFace.NORTH) {
                return BlockFace.EAST;
            }
            if (blockFace == BlockFace.EAST) {
                return BlockFace.SOUTH;
            }
            if (blockFace == BlockFace.SOUTH) {
                return BlockFace.WEST;
            }
            return BlockFace.NORTH;
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
        public BlockFace translateBlockFace(BlockFace blockFace) {
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
        public BlockFace translateBlockFace(BlockFace blockFace) {
            if (blockFace == BlockFace.NORTH) {
                return BlockFace.WEST;
            }
            if (blockFace == BlockFace.WEST) {
                return BlockFace.SOUTH;
            }
            if (blockFace == BlockFace.SOUTH) {
                return BlockFace.EAST;
            }
            return BlockFace.NORTH;
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

    public abstract BlockFace translateBlockFace(BlockFace blockFace);

    public abstract Axis translateAxis(Axis axis);

    public Rotation next() {
        if (this == RIGHT) return BACK;
        if (this == BACK) return LEFT;
        if (this == LEFT) return NONE;
        return RIGHT;
    }

    public static Rotation getNextRotation(@Nullable Rotation rotation) {
        return rotation != null ? rotation.next() : Rotation.NONE;
    }

}
