package io.github.pigaut.voxel.core.structure.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class GetWandSubCommand extends SubCommand {

    public GetWandSubCommand(@NotNull EnhancedPlugin plugin) {
        super("wand", plugin);
        withPermission(plugin.getPermission("structure.wand"));
        withDescription(plugin.getLang("wand-command"));
        withPlayerExecution((player, args, placeholders) -> {
            PlayerUtil.giveItemsOrDrop(player, plugin.getStructures().getWand());
            plugin.sendMessage(player, "received-wand", placeholders);
        });
    }

}
