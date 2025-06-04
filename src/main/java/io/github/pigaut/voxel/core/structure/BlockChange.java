package io.github.pigaut.voxel.core.structure;

import io.github.pigaut.voxel.util.Rotation;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.*;
import org.bukkit.block.data.type.Bed;
import org.jetbrains.annotations.*;

import java.util.*;

public class BlockChange {

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
    private final @Nullable org.bukkit.block.data.type.Bed.Part bedPart;
    private final @Nullable Bamboo.Leaves bambooLeaves;
    private final int offsetX;
    private final int offsetY;
    private final int offsetZ;

    public BlockChange(Material type, @Nullable Integer age, @Nullable BlockFace direction, @NotNull List<BlockFace> facingDirections,
                             @Nullable Axis orientation, @Nullable Boolean open, @Nullable Bisected.Half half, @Nullable Stairs.Shape stairShape,
                             @Nullable Slab.Type slabType, @Nullable Door.Hinge doorHinge, @Nullable org.bukkit.block.data.type.Bed.Part bedPart,
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

    public boolean matchBlock(Location origin, Rotation rotation) {
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

    public void removeBlock(Location origin, Rotation rotation) {
        final Block block = getLocation(origin, rotation).getBlock();
        if (block.getType() != Material.AIR) {
            block.setType(Material.AIR, false);
        }
    }

    public void updateBlock(Location origin, Rotation rotation) {
        final Block block = getLocation(origin, rotation).getBlock();
        BlockData blockData = block.getBlockData();

        if (block.getType() != type) {
            block.setType(type, false);
            blockData = block.getBlockData();
        }

        if (age != null) {
            ((Ageable) blockData).setAge(age);
        }

        if (direction != null) {
            if (blockData instanceof Directional) {
                ((Directional) blockData).setFacing(rotation.translateBlockFace(direction));
            } else {
                ((Rotatable) blockData).setRotation(rotation.translateBlockFace(direction));
            }
        }

        if (facingDirections != null) {
            final MultipleFacing multipleFacing = (MultipleFacing) blockData;
            for (BlockFace facing : rotation.translateBlockFaces(facingDirections)) {
                multipleFacing.setFace(facing, true);
            }
        }

        if (orientation != null) {
            ((Orientable) blockData).setAxis(rotation.translateAxis(orientation));
        }

        if (open != null) {
            ((Openable) blockData).setOpen(open);
        }

        if (half != null) {
            ((Bisected) blockData).setHalf(half);
        }

        if (stairShape != null) {
            ((Stairs) blockData).setShape(stairShape);
        }

        if (slabType != null) {
            ((Slab) blockData).setType(slabType);
        }

        if (doorHinge != null) {
            ((Door) blockData).setHinge(doorHinge);
        }

        if (bedPart != null) {
            ((Bed) blockData).setPart(bedPart);
        }

        if (bambooLeaves != null) {
            ((Bamboo) blockData).setLeaves(bambooLeaves);
        }

        block.setBlockData(blockData, false);
    }

}