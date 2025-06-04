package io.github.pigaut.voxel.core.message.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.core.message.command.parameter.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class BroadcastMessageSubCommand extends SubCommand {

    public BroadcastMessageSubCommand(@NotNull EnhancedPlugin plugin) {
        super("broadcast", plugin);
        withPermission(plugin.getPermission("message.broadcast"));
        withDescription(plugin.getLang("message-broadcast-command"));
        addParameter(new MessageNameParameter(plugin));
        withCommandExecution((sender, args, placeholders) -> {
            final Message message = plugin.getMessage(args[0]);
            if (message == null) {
                plugin.sendMessage(sender, "message-not-found", placeholders);
                return;
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                message.send(player);
            }
            plugin.sendMessage(sender, "sent-message-to-all", placeholders);
        });
    }

}
