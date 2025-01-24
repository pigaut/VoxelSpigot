package io.github.pigaut.voxel.plugin.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import org.jetbrains.annotations.*;

public class ReloadSubCommand extends LangSubCommand {

    public ReloadSubCommand(@NotNull EnhancedPlugin plugin) {
        super("reload", plugin);
        withCommandExecution((sender, args, placeholders) -> {
            try {
                plugin.reload();
            } catch (InvalidConfigurationException e) {
                plugin.sendMessage(sender, "config-error-on-reload", placeholders);
                throw e;
            }
            plugin.sendMessage(sender, "reloading", placeholders);
        });
    }

}
