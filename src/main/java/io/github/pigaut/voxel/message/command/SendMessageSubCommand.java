package io.github.pigaut.voxel.message.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.command.parameter.*;
import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.particle.*;
import io.github.pigaut.voxel.particle.command.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class SendMessageSubCommand extends LangSubCommand {

    public SendMessageSubCommand(@NotNull EnhancedPlugin plugin) {
        super("send-message", plugin);
        addParameter(new OnlinePlayerParameter(plugin));
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
