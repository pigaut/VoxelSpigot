package io.github.pigaut.voxel.message.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.particle.command.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class MessageSubCommand extends LangSubCommand {

    public MessageSubCommand(@NotNull EnhancedPlugin plugin) {
        super("message", plugin);
        addSubCommand(new SendMessageSubCommand(plugin));
        addSubCommand(new BroadcastMessageSubCommand(plugin));
    }

}
