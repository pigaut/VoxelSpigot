package io.github.pigaut.voxel.item.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class ItemSubCommand extends SubCommand {
    public ItemSubCommand(@NotNull EnhancedPlugin plugin) {
        super(plugin.getLang("ITEM_COMMAND", "item"), plugin);
    }
}
