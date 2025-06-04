package io.github.pigaut.voxel.core.message.command.parameter;

import io.github.pigaut.voxel.command.parameter.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class MessageNameParameter extends CommandParameter {

    public MessageNameParameter(@NotNull EnhancedPlugin plugin) {
        super("message-name", (sender, args) -> plugin.getMessages().getAllNames());
    }

}
