package io.github.pigaut.voxel.config.color;

import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.load.*;
import org.bukkit.*;
import org.jetbrains.annotations.*;

public class ColorLoader implements ConfigLoader<Color> {

    @Override
    public @Nullable String getProblemDescription() {
        return "invalid RGB color";
    }

    @Override
    public @NotNull Color loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
        return Color.fromRGB(scalar.toInteger().orThrow());
    }

    @Override
    public @NotNull Color loadFromSection(@NotNull ConfigSection section) throws InvalidConfigurationException {
        final int red = section.getInteger("red").withDefault(0);
        final int green = section.getInteger("green").withDefault(0);
        final int blue = section.getInteger("blue").withDefault(0);
        return Color.fromRGB(red, green, blue);
    }

}
