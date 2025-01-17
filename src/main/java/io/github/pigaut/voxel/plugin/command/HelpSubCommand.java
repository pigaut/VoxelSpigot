package io.github.pigaut.voxel.plugin.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.util.*;
import org.jetbrains.annotations.*;

public class HelpSubCommand extends LangSubCommand {

    public HelpSubCommand(@NotNull EnhancedPlugin plugin) {
        super("help", plugin);
        withPlayerExecution((player, args, placeholders) -> {
            final String header = plugin.getLang("help-header", "------------     %command% Help     ------------");
            final String line = plugin.getLang("help-line", "/%full_command%: %description%");
            final String footer = plugin.getLang("help-footer", "---------------------------------------------");

            Chat.send(player, header, placeholders);
            for (SubCommand subCommand : getParent()) {
                if (!subCommand.isExecutable()) {
                    continue;
                }
                Chat.send(player, line, subCommand);
            }
            Chat.send(player, footer, placeholders);
        });
    }

}
