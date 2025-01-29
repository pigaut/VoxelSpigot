package io.github.pigaut.voxel.meta.placeholder;

import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

public class PlaceholderLoader implements ConfigLoader<Placeholder> {

    @Override
    public @NotNull String getProblemDescription() {
        return "invalid placeholder";
    }

    @Override
    public @NotNull Placeholder loadFromScalar(ConfigScalar scalar) throws InvalidConfigurationException {
        return Placeholder.from(scalar.getKey(), scalar.getValue());
    }

}
