package io.github.pigaut.voxel.command.node;

import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class RootCommand extends CommandNode {

    public RootCommand(@NotNull String name, @NotNull EnhancedPlugin plugin) {
        super(name, plugin);
    }

    @Override
    public boolean isRoot() {
        return true;
    }

    @Override
    public @NotNull RootCommand getRoot() {
        return this;
    }

    @Override
    public @NotNull CommandNode getParent() {
        throw new UnsupportedOperationException("Root command does not have a parent");
    }
}
