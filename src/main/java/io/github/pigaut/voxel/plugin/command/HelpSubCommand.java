package io.github.pigaut.voxel.plugin.command;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class HelpSubCommand extends SubCommand {

    public HelpSubCommand(@NotNull EnhancedPlugin plugin) {
        super("help", plugin);
        withPermission(plugin.getPermission("help"));
        withDescription(plugin.getTranslation("help-command"));
        withCommandExecution((sender, args, placeholders) -> {
            plugin.sendMessage(sender, "help-header", placeholders);
            for (SubCommand subCommand : getParent()) {
                if (subCommand.isExecutable()) {
                    plugin.sendMessage(sender, "help-line", subCommand);
                }
            }
            plugin.sendMessage(sender, "help-footer", placeholders);
        });
    }

}
