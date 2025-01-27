package io.github.pigaut.voxel.message.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class BroadcastMessageSubCommand  extends LangSubCommand {

    public BroadcastMessageSubCommand(@NotNull EnhancedPlugin plugin) {
        super("broadcast-message", plugin);
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
