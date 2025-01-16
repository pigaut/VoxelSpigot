package io.github.pigaut.voxel.item.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class ItemSubCommand extends LangSubCommand {

    public ItemSubCommand(@NotNull EnhancedPlugin plugin) {
        super("item", plugin);
        addSubCommand(new ItemAddSubCommand(plugin));
        addSubCommand(new ItemGetSubCommand(plugin));
    }

}
