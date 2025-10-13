package io.github.pigaut.voxel.config.deserializer;

import com.cryptomorin.xseries.*;
import io.github.pigaut.yaml.configurator.convert.deserialize.*;
import io.github.pigaut.yaml.convert.parse.*;
import org.bukkit.attribute.*;
import org.jetbrains.annotations.*;

public class AttributeDeserializer implements Deserializer<Attribute> {

    @Override
    public @Nullable String getProblemDescription() {
        return "invalid attribute";
    }

    @Override
    public @NotNull Attribute deserialize(@NotNull String attributeName) throws StringParseException {
        final Attribute attribute = XAttribute.of(attributeName).map(XAttribute::get).orElse(null);
        if (attribute == null) {
            throw new StringParseException("Could not find attribute with name: " + attributeName);
        }
        return attribute;
    }

}
