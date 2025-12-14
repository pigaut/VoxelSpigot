package io.github.pigaut.voxel.core.structure.config;

import com.nexomc.nexo.api.*;
import io.github.pigaut.voxel.core.structure.*;
import io.github.pigaut.voxel.hook.craftengine.*;
import io.github.pigaut.voxel.hook.itemsadder.*;
import io.github.pigaut.voxel.hook.nexo.*;
import io.github.pigaut.voxel.server.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.load.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class BlockChangeLoader implements ConfigLoader.Line<BlockChange> {

    @Override
    public @Nullable String getProblemDescription() {
        return "invalid block";
    }

    @Override
    public @NotNull BlockChange loadFromLine(ConfigLine line) throws InvalidConfigurationException {
        int offsetX = line.getInteger("offsetX|offX").withDefault(0);
        int offsetY = line.getInteger("offsetY|offY").withDefault(0);
        int offsetZ = line.getInteger("offsetZ|offZ").withDefault(0);

        if (line.hasFlag("itemsAdderBlock|iaBlock")) {
            if (!SpigotServer.isPluginEnabled("ItemsAdder")) {
                throw new InvalidConfigurationException(line, "itemsAdderBlock", "ItemsAdder is not loaded/enabled");
            }
            dev.lone.itemsadder.api.CustomBlock customBlock =
                    line.getRequired("itemsAdderBlock|iaBlock", dev.lone.itemsadder.api.CustomBlock.class);
            return new ItemsAdderBlockChange(customBlock, offsetX, offsetY, offsetZ);
        }

        if (line.hasFlag("nexoBlock|nxBlock")) {
            if (!SpigotServer.isPluginEnabled("Nexo")) {
                throw new InvalidConfigurationException(line, "nexoBlock", "Nexo is not loaded/enabled");
            }
            String blockId = line.getRequiredString("nexoBlock|nxBlock");
            if (!NexoBlocks.isCustomBlock(blockId)) {
                throw new InvalidConfigurationException(line, "nexoBlock", "Could not find nexo block with name: '" + blockId + "'");
            }
            return new NexoBlockChange(blockId, offsetX, offsetY, offsetZ);
        }

        if (line.hasFlag("craftEngineBlock|ceBlock")) {
            if (!SpigotServer.isPluginEnabled("CraftEngine")) {
                throw new InvalidConfigurationException(line, "craftEngineBlock", "CraftEngine is not loaded/enabled");
            }
            net.momirealms.craftengine.core.block.CustomBlock customBlock =
                    line.getRequired("craftEngineBlock|ceBlock", net.momirealms.craftengine.core.block.CustomBlock.class);
            return new CraftEngineBlockChange(customBlock, offsetX, offsetY, offsetZ);
        }

        Material material = line.getRequired(0, Material.class);
        Integer age = line.getInteger("age").withDefault(null);
        BlockFace direction = line.get("direction|face", BlockFace.class).withDefault(null);
        Axis orientation = line.get("orientation", Axis.class).withDefault(null);
        Boolean open = line.getBoolean("open").withDefault(null);
        Bisected.Half half = line.get("half", Bisected.Half.class).withDefault(null);
        Stairs.Shape stairShape = line.get("stairShape|stairsShape|stairs", Stairs.Shape.class).orElse(null);
        Slab.Type slabType = line.get("slabType|slab", Slab.Type.class).orElse(null);
        Door.Hinge doorHinge = line.get("doorHinge|door", Door.Hinge.class).orElse(null);
        Bed.Part bedPart = line.get("bedPart|bed", Bed.Part.class).orElse(null);
        Bamboo.Leaves bambooLeaves = line.get("bambooLeaves", Bamboo.Leaves.class)
                .orElse(material == Material.BAMBOO ? Bamboo.Leaves.NONE : null);

        return validateAndCreate(line, material, age, direction, List.of(), orientation, open, half,
                stairShape, slabType, doorHinge, bedPart, bambooLeaves, offsetX, offsetY, offsetZ);
    }

    @Override
    public @NotNull BlockChange loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        int offsetX = section.getInteger("offset.x").withDefault(0);
        int offsetY = section.getInteger("offset.y").withDefault(0);
        int offsetZ = section.getInteger("offset.z").withDefault(0);

        if (section.contains("items-adder-block|itemsadder-block|ia-block")) {
            if (!SpigotServer.isPluginEnabled("ItemsAdder")) {
                throw new InvalidConfigurationException(section, "items-adder-block", "ItemsAdder is not loaded/enabled");
            }
            dev.lone.itemsadder.api.CustomBlock customBlock =
                    section.getRequired("items-adder-block|itemsadder-block|ia-block", dev.lone.itemsadder.api.CustomBlock.class);
            return new ItemsAdderBlockChange(customBlock, offsetX, offsetY, offsetZ);
        }

        if (section.contains("nexo-block|nx-block")) {
            if (!SpigotServer.isPluginEnabled("Nexo")) {
                throw new InvalidConfigurationException(section, "nexo-block", "Nexo is not loaded/enabled");
            }
            String blockId = section.getRequiredString("nexo-block|nx-block");
            if (!NexoBlocks.isCustomBlock(blockId)) {
                throw new InvalidConfigurationException(section, "nexo-block", "Could not find nexo block with name: '" + blockId + "'");
            }
            return new NexoBlockChange(blockId, offsetX, offsetY, offsetZ);
        }

        if (section.contains("craft-engine-block|craftengine-block|ce-block")) {
            if (!SpigotServer.isPluginEnabled("CraftEngine")) {
                throw new InvalidConfigurationException(section, "craft-engine-block", "CraftEngine is not loaded/enabled");
            }
            net.momirealms.craftengine.core.block.CustomBlock customBlock =
                    section.getRequired("craft-engine-block|craftengine-block|ce-block", net.momirealms.craftengine.core.block.CustomBlock.class);
            return new CraftEngineBlockChange(customBlock, offsetX, offsetY, offsetZ);
        }

        Material material = section.getRequired("block", Material.class);
        Integer age = section.getInteger("age").withDefault(null);
        BlockFace direction = section.get("direction|face", BlockFace.class).withDefault(null);
        List<BlockFace> facingDirections = section.getAll("directions|faces", BlockFace.class);
        Axis orientation = section.get("orientation", Axis.class).withDefault(null);
        Boolean open = section.getBoolean("open").withDefault(null);
        Bisected.Half half = section.get("half", Bisected.Half.class).withDefault(null);
        Stairs.Shape stairShape = section.get("stair-shape|stairs-shape|stairs", Stairs.Shape.class).orElse(null);
        Slab.Type slabType = section.get("slab-type|slab", Slab.Type.class).orElse(null);
        Door.Hinge doorHinge = section.get("door-hinge|door", Door.Hinge.class).orElse(null);
        Bed.Part bedPart = section.get("bed-part|bed", Bed.Part.class).orElse(null);
        Bamboo.Leaves bambooLeaves = section.get("bamboo-leaves", Bamboo.Leaves.class)
                .orElse(material == Material.BAMBOO ? Bamboo.Leaves.NONE : null);

        return validateAndCreate(section, material, age, direction, facingDirections, orientation, open, half,
                stairShape, slabType, doorHinge, bedPart, bambooLeaves, offsetX, offsetY, offsetZ);
    }

    private static BlockChange validateAndCreate(
            ConfigField field,
            Material material,
            Integer age,
            BlockFace direction,
            List<BlockFace> facingDirections,
            Axis orientation,
            Boolean open,
            Bisected.Half half,
            Stairs.Shape stairShape,
            Slab.Type slabType,
            Door.Hinge doorHinge,
            Bed.Part bedPart,
            Bamboo.Leaves bambooLeaves,
            int offsetX,
            int offsetY,
            int offsetZ
    ) throws InvalidConfigurationException {
        if (!material.isBlock()) {
            throw new InvalidConfigurationException(field, "block", "The material must be a block");
        }

        final BlockData blockData = material.createBlockData();

        if (age != null) {
            if (blockData instanceof Ageable ageable) {
                final int maximumAge = ageable.getMaximumAge();
                if (age < 0 || age > maximumAge) {
                    throw new InvalidConfigurationException(field, "age", "The age for this block must be a value between 0 and " + maximumAge + " (inclusive)");
                }
            }
            else {
                throw new InvalidConfigurationException(field, "age", "The current block is not ageable, please remove the age parameter");
            }
        }

        if (direction != null) {
            if (blockData instanceof Directional directional) {
                if (!directional.getFaces().contains(direction)) {
                    throw new InvalidConfigurationException(field, "direction", "Block cannot face that direction");
                }
            }
            else if (!(blockData instanceof Rotatable)) {
                throw new InvalidConfigurationException(field, "direction", "The current block is not directional, please remove the direction parameter");
            }
        }

        if (!facingDirections.isEmpty()) {
            if (blockData instanceof MultipleFacing multipleFacing) {
                for (BlockFace face : facingDirections) {
                    if (!multipleFacing.getAllowedFaces().contains(face)) {
                        throw new InvalidConfigurationException(field, "directions", "Block cannot face one of those directions");
                    }
                }
            }
            else {
                throw new InvalidConfigurationException(field, "directions", "The current block is not multiple facing, please remove the directions parameter");
            }
        }

        if (orientation != null) {
            if (blockData instanceof Orientable orientable) {
                if (!orientable.getAxes().contains(orientation)) {
                    throw new InvalidConfigurationException(field, "orientation", "Block cannot be oriented to that axis");
                }
            } else {
                throw new InvalidConfigurationException(field, "orientation", "The current block is not orientable, please remove the orientation parameter");
            }
        }

        if (open != null && !(blockData instanceof Openable)) {
            throw new InvalidConfigurationException(field, "open", "The current block is not openable, please remove the open parameter");
        }

        if (half != null && !(blockData instanceof Bisected)) {
            throw new InvalidConfigurationException(field, "half", "The current block is not bisected, please remove the half parameter");
        }

        if (stairShape != null && !(blockData instanceof Stairs)) {
            throw new InvalidConfigurationException(field, "stairs-shape", "The current block is not a stairs, please remove the stairs-shape parameter");
        }

        if (slabType != null && !(blockData instanceof Slab)) {
            throw new InvalidConfigurationException(field, "slab-type", "The current block is not a slab, please remove the slab-type parameter");
        }

        if (doorHinge != null && !(blockData instanceof Door)) {
            throw new InvalidConfigurationException(field, "door-hinge", "The current block is not a door, please remove the door-hinge parameter");
        }

        if (bambooLeaves != null && !(blockData instanceof Bamboo)) {
            throw new InvalidConfigurationException(field, "bamboo-leaves", "The current block is not bamboo, please remove the bamboo-leaves parameter");
        }

        return new GenericBlockChange(material, age, direction, facingDirections, orientation, open, half, stairShape, slabType,
                doorHinge, bedPart, bambooLeaves, offsetX, offsetY, offsetZ);
    }

}
