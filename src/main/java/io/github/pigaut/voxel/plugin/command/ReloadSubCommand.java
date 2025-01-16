package io.github.pigaut.voxel.plugin.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class ReloadSubCommand extends SubCommand {

    public ReloadSubCommand(@NotNull EnhancedPlugin plugin) {
        super(plugin.getLang("RELOAD_COMMAND", "reload"), plugin);
        withDescription(plugin.getLang("RELOAD_DESCRIPTION"));
        withCommandExecution((sender, args, placeholders) -> {
            plugin.reload();
            plugin.sendMessage(sender, "RELOADING", placeholders);
        });
    }

}
