package io.github.pigaut.voxel.core.function;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.section.*;
import org.jetbrains.annotations.*;

import java.io.*;

public class FunctionManager extends ConfigBackedManager.SectionKey<Function> {

    public FunctionManager(@NotNull EnhancedJavaPlugin plugin) {
        super(plugin, "functions");
    }

    @Override
    public void loadFromSectionKey(ConfigSection section, String key) throws InvalidConfigurationException {
        final Function function = section.getRequired(key, Function.class);
        try {
            add(function);
        }
        catch (DuplicateElementException e) {
            throw new InvalidConfigurationException(section, key, e.getMessage());
        }
    }

}
