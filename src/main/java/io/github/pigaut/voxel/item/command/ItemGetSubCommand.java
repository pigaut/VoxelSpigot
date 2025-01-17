package io.github.pigaut.voxel.item.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class ItemGetSubCommand extends LangSubCommand {

    public ItemGetSubCommand(@NotNull EnhancedPlugin plugin) {
        super("get-item", plugin);
        addParameter(new ItemNameParameter(plugin));
        withPlayerExecution((player, args, placeholders) -> {
            final ItemStack item = plugin.getItemStack(args[0]);
            if (item == null) {
                plugin.sendMessage(player, "item-not-found", placeholders);
                return;
            }
            PlayerUtil.giveItemsOrDrop(player, item);
            plugin.sendMessage(player, "received-item", placeholders);
        });
        withPlayerCompletion((player, args) -> plugin.getItems().getItemNames());
    }

}
