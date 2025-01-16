package io.github.pigaut.voxel.item.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class ItemGetSubCommand extends SubCommand {

    public ItemGetSubCommand(@NotNull EnhancedPlugin plugin) {
        super(plugin.getLang("GET_ITEM_COMMAND", "get"), plugin);
        withDescription(plugin.getLang("GET_ITEM_DESCRIPTION"));
        addParameter(plugin.getLang("ITEM_NAME_PARAMETER", "item-name"));
        withPlayerExecution((player, args, placeholders) -> {
            final ItemStack item = plugin.getItemStack(args[0]);
            if (item == null) {
                plugin.sendMessage(player, "ITEM_NOT_FOUND", placeholders);
                return;
            }
            PlayerUtil.giveItemsOrDrop(player, item);
            plugin.sendMessage(player, "RECEIVED_ITEM", placeholders);
        });
        withPlayerCompletion((player, args) -> plugin.getItems().getItemNames());
    }

}
