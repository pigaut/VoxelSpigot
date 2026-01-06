package io.github.pigaut.voxel.core.message.command;

import io.github.pigaut.voxel.command.*;
import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.player.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class SendMessageSubCommand extends SubCommand {

    public SendMessageSubCommand(@NotNull EnhancedPlugin plugin) {
        super("send", plugin);
        withPermission(plugin.getPermission("message.send"));
        withDescription(plugin.getTranslation("message-send-command"));
        withParameter(CommandParameters.messageName(plugin));
        withCommandExecution((sender, args, placeholders) -> {
            Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                plugin.sendMessage(sender, "player-not-online", placeholders);
                return;
            }
            Message message = plugin.getMessage(args[1]);
            if (message == null) {
                plugin.sendMessage(sender, "message-not-found", placeholders);
                return;
            }
            PlayerState playerState = plugin.getPlayerState(player);
            message.send(playerState);
            plugin.sendMessage(sender, "sent-message-to-player", placeholders);
        });
    }

}
