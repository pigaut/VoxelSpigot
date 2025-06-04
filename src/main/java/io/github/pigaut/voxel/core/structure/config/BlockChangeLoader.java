package io.github.pigaut.voxel.core.structure.config;

import io.github.pigaut.voxel.core.structure.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class BlockChangeLoader implements ConfigLoader<BlockChange> {

    @Override
    public @Nullable String getProblemDescription() {
        return "invalid block";
    }

    @Override
    public @NotNull BlockChange loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        final Material material = section.get("block", Material.class);
        final Integer age = section.getOptionalInteger("age").orElse(null);
        final BlockFace direction = section.getOptional("direction|face", BlockFace.class).orElse(null);
        final List<BlockFace> facingDirections = section.getList("directions|faces", BlockFace.class);
        final Axis orientation = section.getOptional("orientation", Axis.class).orElse(null);
        final Boolean open = section.getOptionalBoolean("open").orElse(null);
        final Bisected.Half half = section.getOptional("half", Bisected.Half.class).orElse(null);
        final Stairs.Shape stairShape = section.getOptional("stair-shape|stairs-shape|stairs", Stairs.Shape.class).orElse(null);
        final Slab.Type slabType = section.getOptional("slab-type|slab", Slab.Type.class).orElse(null);
        final Door.Hinge doorHinge = section.getOptional("door-hinge|door", Door.Hinge.class).orElse(null);
        final Bed.Part bedPart = section.getOptional("bed-part|bed", Bed.Part.class).orElse(null);
        final Bamboo.Leaves bambooLeaves = section.getOptional("bamboo-leaves", Bamboo.Leaves.class)
                .orElse(material == Material.BAMBOO ? Bamboo.Leaves.NONE : null);

        if (!material.isBlock()) {
            throw new InvalidConfigurationException(section, "block", "The material must be a block");
        }

        final BlockData blockData = material.createBlockData();

        if (age != null) {
            if (blockData instanceof Ageable ageable) {
                final int maximumAge = ageable.getMaximumAge();
                if (age < 0 || age > maximumAge) {
                    throw new InvalidConfigurationException(section, "age", "The age for this block must be a value between 0 and " + maximumAge + " (inclusive)");
                }
            }
            else {
                throw new InvalidConfigurationException(section, "age", "The current block is not ageable, please remove the age parameter");
            }
        }

        if (direction != null) {
            if (blockData instanceof Directional directional) {
                if (!directional.getFaces().contains(direction)) {
                    throw new InvalidConfigurationException(section, "direction", "Block cannot face that direction");
                }
            }
            else if (!(blockData instanceof Rotatable)) {
                throw new InvalidConfigurationException(section, "direction", "The current block is not directional, please remove the direction parameter");
            }
        }

        if (!facingDirections.isEmpty()) {
            if (blockData instanceof MultipleFacing multipleFacing) {
                for (BlockFace face : facingDirections) {
                    if (!multipleFacing.getAllowedFaces().contains(face)) {
                        throw new InvalidConfigurationException(section, "directions", "Block cannot face one of those directions");
                    }
                }
            }
            else {
                throw new InvalidConfigurationException(section, "directions", "The current block is not multiple facing, please remove the directions parameter");
            }
        }

        if (orientation != null) {
            if (blockData instanceof Orientable orientable) {
                if (!orientable.getAxes().contains(orientation)) {
                    throw new InvalidConfigurationException(section, "orientation", "Block cannot be oriented to that axis");
                }
            } else {
                throw new InvalidConfigurationException(section, "orientation", "The current block is not orientable, please remove the orientation parameter");
            }
        }

        if (open != null && !(blockData instanceof Openable)) {
            throw new InvalidConfigurationException(section, "open", "The current block is not openable, please remove the open parameter");
        }

        if (half != null && !(blockData instanceof Bisected)) {
            throw new InvalidConfigurationException(section, "half", "The current block is not bisected, please remove the half parameter");
        }

        if (stairShape != null && !(blockData instanceof Stairs)) {
            throw new InvalidConfigurationException(section, "stairs-shape", "The current block is not a stairs, please remove the stairs-shape parameter");
        }

        if (slabType != null && !(blockData instanceof Slab)) {
            throw new InvalidConfigurationException(section, "slab-type", "The current block is not a slab, please remove the slab-type parameter");
        }

        if (doorHinge != null && !(blockData instanceof Door)) {
            throw new InvalidConfigurationException(section, "door-hinge", "The current block is not a door, please remove the door-hinge parameter");
        }

        if (bambooLeaves != null && !(blockData instanceof Bamboo)) {
            throw new InvalidConfigurationException(section, "bamboo-leaves", "The current block is not bamboo, please remove the bamboo-leaves parameter");
        }

        final int offsetX = section.getOptionalInteger("offset.x").orElse(0);
        final int offsetY = section.getOptionalInteger("offset.y").orElse(0);
        final int offsetZ = section.getOptionalInteger("offset.z").orElse(0);

        return new BlockChange(material, age, direction, facingDirections, orientation, open, half, stairShape, slabType,
                doorHinge, bedPart, bambooLeaves, offsetX, offsetY, offsetZ);
    }

}
