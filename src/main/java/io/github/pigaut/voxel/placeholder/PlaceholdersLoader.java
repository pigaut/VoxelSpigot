package io.github.pigaut.voxel.placeholder;

import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.configurator.loader.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PlaceholdersLoader implements ConfigLoader<Placeholder[]> {

    @Override
    public @NotNull String getProblemDescription() {
        return "invalid placeholders";
    }

    @Override
    public Placeholder @NotNull [] loadFromSection(@NotNull ConfigSection config) throws InvalidConfigurationException {
        final List<Placeholder> placeholders = new ArrayList<>();
        for (String key : config.getKeys()) {
            placeholders.add(Placeholder.create(key, '{', '}', config.getScalar(key).getValue()));
        }
        return placeholders.toArray(new Placeholder[0]);
    }

}
