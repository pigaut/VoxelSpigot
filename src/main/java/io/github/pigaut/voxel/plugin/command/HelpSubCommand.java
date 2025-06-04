package io.github.pigaut.voxel.plugin.command;

import io.github.pigaut.voxel.bukkit.*;
import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.plugin.*;
import org.jetbrains.annotations.*;

public class HelpSubCommand extends SubCommand {

    public HelpSubCommand(@NotNull EnhancedPlugin plugin) {
        super("help", plugin);
        withPermission(plugin.getPermission("help"));
        withDescription(plugin.getLang("help-command"));
        withCommandExecution((sender, args, placeholders) -> {
            final String header = plugin.getLang("help-header", "------------     {command_name_tc} Help     ------------");
            final String line = plugin.getLang("help-line", "/{command}: {command_description}");
            final String footer = plugin.getLang("help-footer", "---------------------------------------------");

            Chat.send(sender, header, placeholders);
            for (SubCommand subCommand : getParent()) {
                if (!subCommand.isExecutable()) {
                    continue;
                }
                Chat.send(sender, line, subCommand);
            }
            Chat.send(sender, footer, placeholders);
        });
    }

}
