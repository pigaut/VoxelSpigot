package io.github.pigaut.voxel.config.attribute.legacy;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.util.reflection.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.map.*;
import org.bukkit.attribute.*;
import org.jetbrains.annotations.*;

public class AttributeMapperLegacy implements ConfigMapper.Line<ItemAttribute> {

    public AttributeMapperLegacy() {
        if (!Reflection.onClass(AttributeModifier.class).matchMethod("getSlot")) {
            throw new IllegalStateException("Expected AttributeModifier#getSlot() method was not found");
        }
    }

    @Override
    public void mapToLine(@NotNull ConfigLine line, @NotNull ItemAttribute itemAttribute) {
        final AttributeModifier modifier = itemAttribute.modifier();
        line.set(0, itemAttribute.attribute());
        line.set(1, modifier.getAmount());
        line.setFlag("operation", AttributeOperation.of(modifier.getOperation()));
        line.setFlag("slot", Reflection.on(modifier).call("getSlot").get());
        line.setFlag("name", modifier.getName());
    }

    @Override
    public void mapToSection(@NotNull ConfigSection section, @NotNull ItemAttribute itemAttribute) {
        final AttributeModifier modifier = itemAttribute.modifier();
        section.set("attribute|type", itemAttribute.attribute());
        section.set("amount", modifier.getAmount());
        section.set("slot", Reflection.on(modifier).call("getSlot").get());
        section.set("operation", modifier.getOperation());
        section.set("name", modifier.getName());
    }

}
