package io.github.pigaut.voxel.core.message.impl;

import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import org.jetbrains.annotations.*;

public abstract class GenericMessage implements Message, Identifiable {

    private final String name;
    private final String group;
    private final ConfigSection section;

    protected GenericMessage(String name, @Nullable String group, ConfigSection section) {
        this.name = name;
        this.group = group;
        this.section = section;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @Nullable String getGroup() {
        return group;
    }

    @Override
    public @NotNull ConfigField getField() {
        return section;
    }

}
