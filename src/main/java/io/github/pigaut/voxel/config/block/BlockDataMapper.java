package io.github.pigaut.voxel.config.block;

import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.*;
import io.github.pigaut.yaml.configurator.map.*;
import org.bukkit.block.*;
import org.bukkit.block.data.*;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.*;
import org.jetbrains.annotations.*;

public class BlockDataMapper implements ConfigMapper<Block> {

    @Override
    public @NotNull FieldType getDefaultMappingType() {
        return FieldType.SECTION;
    }

    @Override
    public void mapToSection(@NotNull ConfigSection section, @NotNull Block block) {
        section.set("block", block.getType());

        final BlockData blockData = block.getBlockData();

        if (blockData instanceof Ageable ageable) {
            section.set("age", ageable.getAge());
        }

        if (blockData instanceof Directional directional) {
            section.set("direction|face", directional.getFacing());
        } else if (blockData instanceof Rotatable rotatable) {
            section.set("direction|face", rotatable.getRotation());
        }

        if (blockData instanceof MultipleFacing multipleFacing) {
            section.set("directions|faces", multipleFacing.getFaces());
        }

        if (blockData instanceof Orientable orientable) {
            section.set("orientation", orientable.getAxis());
        }

        if (blockData instanceof Openable openable) {
            section.set("open", openable.isOpen());
        }

        if (blockData instanceof Bisected bisected) {
            section.set("half", bisected.getHalf());
        }

        if (blockData instanceof Stairs stairs) {
            section.set("stair-shape|stairs-shape|stairs", stairs.getShape());
        }

        if (blockData instanceof Slab slab) {
            section.set("slab-type|slab", slab.getType());
        }

        if (blockData instanceof Door door) {
            section.set("door-hinge|door", door.getHinge());
        }

        if (blockData instanceof Bed bed) {
            section.set("bed-part|bed", bed.getPart());
        }

        if (blockData instanceof Bamboo bamboo) {
            section.set("bamboo-leaves", bamboo.getLeaves());
        }
    }

}
