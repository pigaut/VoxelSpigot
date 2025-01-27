package io.github.pigaut.voxel.message.command;

import io.github.pigaut.voxel.command.parameter.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class MessageNameParameter extends CommandParameter {

    public MessageNameParameter(@NotNull EnhancedPlugin plugin) {
        super(plugin.getLang("message-name-parameter"), (sender, args) -> plugin.getMessages().getMessageNames());
    }

}
