package io.github.pigaut.voxel.plugin.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.voxel.util.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.*;
import io.github.pigaut.yaml.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;

public class ReloadSubCommand extends SubCommand {

    public ReloadSubCommand(@NotNull EnhancedJavaPlugin plugin) {
        super("reload", plugin);
        withPermission(plugin.getPermission("reload"));
        withDescription(plugin.getLang("reload-command"));
        withCommandExecution((sender, args, placeholders) -> {
            try {
                plugin.reload(errorsFound -> {
                    if (sender instanceof Player player) {
                        plugin.logConfigurationErrors(player, errorsFound);
                    }
                    else {
                        plugin.logConfigurationErrors(null, errorsFound);
                    }
                    plugin.sendMessage(sender, "reload-complete", placeholders);
                });
            } catch (PluginReloadInProgressException e) {
                plugin.sendMessage(sender, "already-reloading", placeholders);
                return;
            }
            plugin.sendMessage(sender, "reloading", placeholders);
        });
    }


}
