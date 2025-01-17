package io.github.pigaut.voxel.plugin.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class ReloadSubCommand extends LangSubCommand {

    public ReloadSubCommand(@NotNull EnhancedPlugin plugin) {
        super("reload", plugin);
        withCommandExecution((sender, args, placeholders) -> {
            plugin.reload();
            plugin.sendMessage(sender, "reloading", placeholders);
        });
    }

}
