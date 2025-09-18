package io.github.pigaut.voxel.config.attribute;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.load.*;
import org.bukkit.*;
import org.bukkit.attribute.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

// Attribute Loader for 1.21.3+
public class AttributeLoader implements ConfigLoader.Line<ItemAttribute> {

    @Override
    public @NotNull String getProblemDescription() {
        return "invalid item attribute modifier";
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public @NotNull ItemAttribute loadFromLine(ConfigLine line) throws InvalidConfigurationException {
        Attribute attribute = line.getRequired(0, Attribute.class);

        var namespacedKey = NamespacedKey.fromString(line.getString("name").throwOrElse(UUID.randomUUID().toString()));
        var amount = line.getRequiredDouble(1);
        var operation = line.get("operation", AttributeOperation.class).throwOrElse(AttributeOperation.ADD_VALUE);
        var slot = line.get("slot", EquipmentSlotGroup.class).throwOrElse(EquipmentSlotGroup.HAND);
        AttributeModifier modifier = new AttributeModifier(namespacedKey, amount, operation.getOperation(), slot);

        return new ItemAttribute(attribute, modifier);
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public @NotNull ItemAttribute loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        Attribute attribute = section.getRequired("type|attribute", Attribute.class);

        var namespacedKey = NamespacedKey.fromString(section.getString("name").throwOrElse(UUID.randomUUID().toString()));
        var amount = section.getRequiredDouble("amount");
        var operation = section.get("operation", AttributeOperation.class).throwOrElse(AttributeOperation.ADD_VALUE);
        var slot = section.get("slot", EquipmentSlotGroup.class).throwOrElse(EquipmentSlotGroup.HAND);
        AttributeModifier modifier = new AttributeModifier(namespacedKey, amount, operation.getOperation(), slot);

        return new ItemAttribute(attribute, modifier);
    }

}
