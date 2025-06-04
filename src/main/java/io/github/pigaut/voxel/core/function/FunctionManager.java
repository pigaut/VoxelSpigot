package io.github.pigaut.voxel.core.function;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.*;
import io.github.pigaut.yaml.node.section.*;
import org.jetbrains.annotations.*;

import java.io.*;

public class FunctionManager extends ManagerContainer<Function> {

    public FunctionManager(@NotNull EnhancedJavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public @Nullable String getFilesDirectory() {
        return "functions";
    }

    @Override
    public void loadFile(@NotNull File file) {
        final RootSection config = new RootSection(file, plugin.getConfigurator());
        config.setPrefix("Function");
        config.load();

        for (String key : config.getKeys()) {
            final Function function = config.get(key, Function.class);
            this.add(function);
        }
    }

}
