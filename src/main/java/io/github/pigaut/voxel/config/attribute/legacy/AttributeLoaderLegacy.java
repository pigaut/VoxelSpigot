package io.github.pigaut.voxel.config.attribute.legacy;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.util.reflection.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.load.*;
import org.bukkit.attribute.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

//Attribute loader for <1.21.3
public class AttributeLoaderLegacy implements ConfigLoader.Line<ItemAttribute> {

    private final Reflection attributeReflection = Reflection.onClass(AttributeModifier.class);

    public AttributeLoaderLegacy() {
        if (!Reflection.onClass(AttributeModifier.class)
                .matchConstructor(UUID.class, String.class, double.class, AttributeModifier.Operation.class, EquipmentSlot.class)) {
            throw new IllegalStateException("Expected AttributeModifier(UUID, String, double, Operation, EquipmentSlot) constructor was not found");
        }
    }

    @Override
    public @NotNull String getProblemDescription() {
        return "invalid item attribute modifier";
    }

    @Override
    @SuppressWarnings("")
    public @NotNull ItemAttribute loadFromLine(ConfigLine line) throws InvalidConfigurationException {
        Attribute attribute = line.getRequired(0, Attribute.class);

        var name = line.getString("name").throwOrElse("");
        var amount = line.getRequiredDouble(1);
        var operation = line.get("operation", AttributeOperation.class).throwOrElse(AttributeOperation.ADD_VALUE);
        var slot = line.get("slot", EquipmentSlot.class).throwOrElse(EquipmentSlot.HAND);
        AttributeModifier modifier = attributeReflection.create(UUID.randomUUID(), name, amount, operation.getOperation(), slot).get();

        return new ItemAttribute(attribute, modifier);
    }

    @Override
    public @NotNull ItemAttribute loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        Attribute attribute = section.getRequired("type|attribute", Attribute.class);

        var name = section.getString("name").throwOrElse("");
        var amount = section.getRequiredDouble("amount");
        var operation = section.get("operation", AttributeOperation.class).throwOrElse(AttributeOperation.ADD_VALUE);
        var slot = section.get("slot", EquipmentSlot.class).throwOrElse(EquipmentSlot.HAND);
        AttributeModifier modifier = attributeReflection.create(UUID.randomUUID(), name, amount, operation.getOperation(), slot).get();

        return new ItemAttribute(attribute, modifier);
    }

}
