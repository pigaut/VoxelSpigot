package io.github.pigaut.voxel.core.structure;

import io.github.pigaut.voxel.bukkit.Rotation;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class GenericBlockChange implements BlockChange {

    private final Material type;
    private final @Nullable Integer age;
    private final @Nullable BlockFace direction;
    private final @Nullable Set<BlockFace> facingDirections;
    private final @Nullable Axis orientation;
    private final @Nullable Boolean open;
    private final @Nullable Bisected.Half half;
    private final @Nullable Stairs.Shape stairShape;
    private final @Nullable Slab.Type slabType;
    private final @Nullable Door.Hinge doorHinge;
    private final @Nullable Bed.Part bedPart;
    private final @Nullable Bamboo.Leaves bambooLeaves;
    private final int offsetX;
    private final int offsetY;
    private final int offsetZ;

    public GenericBlockChange(Material type, @Nullable Integer age, @Nullable BlockFace direction, @NotNull List<BlockFace> facingDirections,
                              @Nullable Axis orientation, @Nullable Boolean open, @Nullable Bisected.Half half, @Nullable Stairs.Shape stairShape,
                              @Nullable Slab.Type slabType, @Nullable Door.Hinge doorHinge, @Nullable Bed.Part bedPart,
                              @Nullable Bamboo.Leaves bambooLeaves, int offsetX, int offsetY, int offsetZ) {
        this.type = type;
        this.age = age;
        this.direction = direction;
        this.facingDirections = facingDirections.isEmpty() ? null : new HashSet<>(facingDirections);
        this.orientation = orientation;
        this.open = open;
        this.half = half;
        this.stairShape = stairShape;
        this.slabType = slabType;
        this.doorHinge = doorHinge;
        this.bedPart = bedPart;
        this.bambooLeaves = bambooLeaves;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }

    public Material getType() {
        return type;
    }

    public @Nullable Integer getAge() {
        return age;
    }

    public @Nullable BlockFace getDirection() {
        return direction;
    }

    public @Nullable Set<BlockFace> getFacingDirections() {
        return facingDirections;
    }

    public @Nullable Axis getOrientation() {
        return orientation;
    }

    public @Nullable Boolean getOpen() {
        return open;
    }

    public @Nullable Bisected.Half getHalf() {
        return half;
    }

    public @Nullable Stairs.Shape getStairShape() {
        return stairShape;
    }

    public @Nullable Slab.Type getSlabType() {
        return slabType;
    }

    public @Nullable Door.Hinge getDoorHinge() {
        return doorHinge;
    }

    public @Nullable org.bukkit.block.data.type.Bed.Part getBedPart() {
        return bedPart;
    }

    public @Nullable Bamboo.Leaves getBambooLeaves() {
        return bambooLeaves;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public int getOffsetZ() {
        return offsetZ;
    }

    public @NotNull Location getLocation(Location origin, Rotation rotation) {
        return rotation.apply(origin.clone(), offsetX, offsetY, offsetZ);
    }

    public boolean isPlaced(@NotNull Location origin, @NotNull Rotation rotation) {
        final BlockData blockData = getLocation(origin, rotation).getBlock().getBlockData();
        if (blockData.getMaterial() != type) {
            return false;
        }

        if (age != null && ((Ageable) blockData).getAge() != age) {
            return false;
        }

        if (direction != null) {
            if (blockData instanceof Directional directional) {
                if (directional.getFacing() != rotation.translateBlockFace(direction)) {
                    return false;
                }
            } else if (((Rotatable) blockData).getRotation() != rotation.translateBlockFace(direction)) {
                return false;
            }
        }

        if (facingDirections != null
                && !((MultipleFacing) blockData).getFaces().equals(rotation.translateBlockFaces(facingDirections))) {
            return false;
        }

        if (orientation != null && ((Orientable) blockData).getAxis() != rotation.translateAxis(orientation)) {
            return false;
        }

        if (half != null && half != ((Bisected) blockData).getHalf()) {
            return false;
        }

        if (stairShape != null && stairShape != ((Stairs) blockData).getShape()) {
            return false;
        }

        if (slabType != null && slabType != ((Slab) blockData).getType()) {
            return false;
        }

        if (doorHinge != null && doorHinge != ((Door) blockData).getHinge()) {
            return false;
        }

        if (bedPart != null && bedPart != ((org.bukkit.block.data.type.Bed) blockData).getPart()) {
            return false;
        }

        if (bambooLeaves != null && bambooLeaves != ((Bamboo) blockData).getLeaves()) {
            return false;
        }

        return true;
    }

    public @NotNull Block getBlock(Location origin, Rotation rotation) {
        return getLocation(origin, rotation).getBlock();
    }

    public void remove(@NotNull Location origin, @NotNull Rotation rotation) {
        Block block = getLocation(origin, rotation).getBlock();
        if (block.getType() != Material.AIR) {
            block.setType(Material.AIR, false);
        }
    }

    public void place(@NotNull Location origin, @NotNull Rotation rotation) {
        Block block = getLocation(origin, rotation).getBlock();
        BlockData blockData = block.getBlockData();

        if (block.getType() != type) {
            block.setType(type, false);
            blockData = block.getBlockData();
        }

        updateBlockData(block, blockData, rotation);
    }

    private void updateBlockData(Block block, BlockData blockData, Rotation rotation) {
        boolean changed = false;

        if (age != null) {
            Ageable ageable = (Ageable) blockData;
            if (ageable.getAge() != age) {
                ageable.setAge(age);
                changed = true;
            }
        }

        if (direction != null) {
            BlockFace translated = rotation.translateBlockFace(direction);
            if (blockData instanceof Directional directional) {
                if (directional.getFacing() != translated) {
                    directional.setFacing(translated);
                    changed = true;
                }
            } else if (blockData instanceof Rotatable rotatable) {
                if (rotatable.getRotation() != translated) {
                    rotatable.setRotation(translated);
                    changed = true;
                }
            }
        }

        if (facingDirections != null) {
            MultipleFacing multipleFacing = (MultipleFacing) blockData;
            for (BlockFace facing : rotation.translateBlockFaces(facingDirections)) {
                if (!multipleFacing.hasFace(facing)) {
                    multipleFacing.setFace(facing, true);
                    changed = true;
                }
            }
        }

        if (orientation != null) {
            Orientable orientable = (Orientable) blockData;
            Axis axis = rotation.translateAxis(orientation);
            if (orientable.getAxis() != axis) {
                orientable.setAxis(axis);
                changed = true;
            }
        }

        if (open != null) {
            Openable openable = (Openable) blockData;
            if (openable.isOpen() != open) {
                openable.setOpen(open);
                changed = true;
            }
        }

        if (half != null) {
            Bisected bisected = (Bisected) blockData;
            if (bisected.getHalf() != half) {
                bisected.setHalf(half);
                changed = true;
            }
        }

        if (stairShape != null) {
            Stairs stairs = (Stairs) blockData;
            if (stairs.getShape() != stairShape) {
                stairs.setShape(stairShape);
                changed = true;
            }
        }

        if (slabType != null) {
            Slab slab = (Slab) blockData;
            if (slab.getType() != slabType) {
                slab.setType(slabType);
                changed = true;
            }
        }

        if (doorHinge != null) {
            Door door = (Door) blockData;
            if (door.getHinge() != doorHinge) {
                door.setHinge(doorHinge);
                changed = true;
            }
        }

        if (bedPart != null) {
            Bed bed = (Bed) blockData;
            if (bed.getPart() != bedPart) {
                bed.setPart(bedPart);
                changed = true;
            }
        }

        if (bambooLeaves != null) {
            Bamboo bamboo = (Bamboo) blockData;
            if (bamboo.getLeaves() != bambooLeaves) {
                bamboo.setLeaves(bambooLeaves);
                changed = true;
            }
        }

        if (changed) {
            block.setBlockData(blockData, false);
        }
    }

}
