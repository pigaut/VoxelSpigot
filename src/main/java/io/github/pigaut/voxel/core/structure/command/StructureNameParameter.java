package io.github.pigaut.voxel.core.structure.command;

import io.github.pigaut.voxel.command.parameter.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class StructureNameParameter extends CommandParameter {

    public StructureNameParameter(@NotNull EnhancedPlugin plugin) {
        super("structure-name", (sender, args) -> plugin.getStructures().getAllNames());
    }

}
