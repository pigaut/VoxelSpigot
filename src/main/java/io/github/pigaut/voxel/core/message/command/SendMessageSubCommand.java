package io.github.pigaut.voxel.core.message.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.command.parameter.*;
import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.core.message.command.parameter.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class SendMessageSubCommand extends SubCommand {

    public SendMessageSubCommand(@NotNull EnhancedPlugin plugin) {
        super("send", plugin);
        withPermission(plugin.getPermission("message.send"));
        withDescription(plugin.getLang("message-send-command"));
        addParameter(new OnlinePlayerParameter());
        addParameter(new MessageNameParameter(plugin));
        withCommandExecution((sender, args, placeholders) -> {
            final Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                plugin.sendMessage(sender, "player-not-online", placeholders);
                return;
            }
            final Message message = plugin.getMessage(args[1]);
            if (message == null) {
                plugin.sendMessage(sender, "message-not-found", placeholders);
                return;
            }
            message.send(player);
            plugin.sendMessage(sender, "sent-message-to-player", placeholders);
        });
    }

}
