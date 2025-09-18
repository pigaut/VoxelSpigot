package io.github.pigaut.voxel.config.attribute;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.map.*;
import org.bukkit.attribute.*;
import org.jetbrains.annotations.*;

// Attribute Mapper for 1.21.3+
public class AttributeMapper implements ConfigMapper.Line<ItemAttribute> {

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void mapToLine(@NotNull ConfigLine line, @NotNull ItemAttribute itemAttribute) {
        final AttributeModifier modifier = itemAttribute.modifier();
        line.set(0, itemAttribute.attribute());
        line.set(1, modifier.getAmount());
        line.setFlag("operation", AttributeOperation.of(modifier.getOperation()));
        line.setFlag("slot", modifier.getSlotGroup());
        line.setFlag("name", modifier.getName());
    }

    @Override
    public void mapToSection(@NotNull ConfigSection section, @NotNull ItemAttribute itemAttribute) {
        final AttributeModifier modifier = itemAttribute.modifier();
        section.set("attribute|type", itemAttribute.attribute());
        section.set("amount", modifier.getAmount());
        section.set("slot", modifier.getSlotGroup());
        section.set("operation", modifier.getOperation());
        section.set("name", modifier.getName());
    }

}
