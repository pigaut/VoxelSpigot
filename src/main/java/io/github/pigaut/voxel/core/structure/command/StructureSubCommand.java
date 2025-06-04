package io.github.pigaut.voxel.core.structure.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class StructureSubCommand extends SubCommand {

    public StructureSubCommand(@NotNull EnhancedPlugin plugin) {
        super("structure", plugin);
        withPermission(plugin.getPermission("structure"));
        withDescription(plugin.getLang("structure-command"));
        addSubCommand(new StructureSaveSubCommand(plugin));
        addSubCommand(new StructurePlaceSubCommand(plugin));
    }

}
