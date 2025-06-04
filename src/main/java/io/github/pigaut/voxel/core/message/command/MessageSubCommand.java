package io.github.pigaut.voxel.core.message.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class MessageSubCommand extends SubCommand {

    public MessageSubCommand(@NotNull EnhancedPlugin plugin) {
        super("message", plugin);
        withPermission(plugin.getPermission("message"));
        withDescription(plugin.getLang("message-command"));
        addSubCommand(new SendMessageSubCommand(plugin));
        addSubCommand(new BroadcastMessageSubCommand(plugin));
    }

}
