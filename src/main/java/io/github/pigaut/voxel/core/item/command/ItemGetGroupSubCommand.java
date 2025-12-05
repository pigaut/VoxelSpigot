package io.github.pigaut.voxel.core.item.command;

import io.github.pigaut.voxel.command.*;
import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.core.item.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ItemGetGroupSubCommand extends SubCommand {

    public ItemGetGroupSubCommand(@NotNull EnhancedPlugin plugin) {
        super("get-group", plugin);
        withPermission(plugin.getPermission("item.get-group"));
        withDescription(plugin.getTranslation("item-get-group-command"));
        withParameter(CommandParameters.itemGroup(plugin));
        withPlayerExecution((player, args, placeholders) -> {
            final List<Item> groupItems = plugin.getItems().getAll(args[0]);
            if (groupItems.isEmpty()) {
                plugin.sendMessage(player, "item-group-not-found", placeholders);
                return;
            }
            for (Item item : groupItems) {
                PlayerUtil.giveItemsOrDrop(player, item.getItemStack());
            }
            plugin.sendMessage(player, "received-item-group");
        });
    }

}
