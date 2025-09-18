package io.github.pigaut.voxel.core.message.impl;

import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import org.jetbrains.annotations.*;

public abstract class GenericMessage implements Message, Identifiable {

    private final String name;
    private final String group;

    protected GenericMessage(String name, @Nullable String group) {
        this.name = name;
        this.group = group;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @Nullable String getGroup() {
        return group;
    }

}
