package io.github.pigaut.voxel.config.attribute;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.*;
import io.github.pigaut.yaml.configurator.mapper.*;
import org.jetbrains.annotations.*;
import org.snakeyaml.engine.v2.common.*;

public class AttributeMapper implements ConfigMapper<Attribute> {

    private final boolean compact;

    public AttributeMapper(boolean compact) {
        this.compact = compact;
    }

    @Override
    public @NotNull FieldType getDefaultMappingType() {
        return compact ? FieldType.SEQUENCE : FieldType.SECTION;
    }

    @Override
    public void mapSection(@NotNull ConfigSection section, @NotNull Attribute attribute) {
        section.set("attribute|type", attribute.getAttributeType());
        section.set("name", attribute.getName());
        section.set("amount", attribute.getAmount());
        section.set("slot", attribute.getSlot());
        section.set("operation", attribute.getOperation());
    }

    @Override
    public void mapSequence(@NotNull ConfigSequence sequence, @NotNull Attribute attribute) {
        sequence.add(attribute.getAttributeType());
        final String name = attribute.getName();
        if (!name.isEmpty()) {
            sequence.add(name);
        }
        sequence.add(attribute.getAmount());
        sequence.add(attribute.getSlot());
        sequence.add(attribute.getOperation());
        sequence.setFlowStyle(FlowStyle.FLOW);
    }

}
