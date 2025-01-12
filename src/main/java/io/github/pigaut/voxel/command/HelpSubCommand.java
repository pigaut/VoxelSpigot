package io.github.pigaut.voxel.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.parser.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class HelpSubCommand extends SubCommand {

    public HelpSubCommand(@NotNull EnhancedPlugin plugin) {
        super(plugin.getLang("HELP_COMMAND", "help"), plugin);

        withDescription(plugin.getLang("HELP_DESCRIPTION"));
        withPlayerExecution((player, args) -> {
            final String header = plugin.getLang("HELP_HEADER", "&2------------     &r&l%command% Help     &2------------");
            final String line = plugin.getLang("HELP_LINE", "&a/%command%: &r%description%");
            final String footer = plugin.getLang("HELP_FOOTER", "&2---------------------------------------------");

            Chat.send(player, header, this);
            for (SubCommand subCommand : getParent()) {
                if (!subCommand.isExecutable()) {
                    continue;
                }
                Chat.send(player, line, subCommand);
            }
            Chat.send(player, footer, this);
        });
    }

}
