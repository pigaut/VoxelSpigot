package io.github.pigaut.voxel.config.color;

import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class ColorLoader implements ConfigLoader<Color> {

    @Override
    public @Nullable String getProblemDescription() {
        return "invalid RGB color";
    }

    @Override
    public @NotNull Color loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
        return Color.fromRGB(scalar.toInteger());
    }

    @Override
    public @NotNull Color loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        final int red = section.getOptionalInteger("red").orElse(0);
        final int green = section.getOptionalInteger("green").orElse(0);
        final int blue = section.getOptionalInteger("blue").orElse(0);
        return Color.fromRGB(red, green, blue);
    }

}
