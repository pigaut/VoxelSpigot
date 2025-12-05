package io.github.pigaut.voxel.core.function;

import io.github.pigaut.voxel.plugin.boot.*;
import io.github.pigaut.voxel.plugin.boot.phase.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class FunctionManager extends ConfigBackedManager.SectionKey<Function> {

    public FunctionManager(@NotNull EnhancedJavaPlugin plugin) {
        super(plugin, "functions");
    }

    @Override
    public void loadFromSectionKey(ConfigSection section, String key) throws InvalidConfigurationException {
        Function function = section.getRequired(key, Function.class);
        try {
            add(function);
        }
        catch (DuplicateElementException e) {
            throw new InvalidConfigurationException(section, key, e.getMessage());
        }
    }

}
