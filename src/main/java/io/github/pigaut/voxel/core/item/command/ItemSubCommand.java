package io.github.pigaut.voxel.core.item.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class ItemSubCommand extends SubCommand {

    public ItemSubCommand(@NotNull EnhancedPlugin plugin) {
        super("item", plugin);
        this.withPermission(plugin.getPermission("item"));
        this.withDescription(plugin.getLang("item-command"));
        this.addSubCommand(new ItemSaveSubCommand(plugin));
        this.addSubCommand(new ItemGetSubCommand(plugin));
        this.addSubCommand(new ItemGetGroupSubCommand(plugin));
    }

}
