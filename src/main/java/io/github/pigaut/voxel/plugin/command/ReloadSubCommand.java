package io.github.pigaut.voxel.plugin.command;

import io.github.pigaut.voxel.*;
import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.config.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class ReloadSubCommand extends SubCommand {

    public ReloadSubCommand(@NotNull EnhancedJavaPlugin plugin) {
        super("reload", plugin);
        withPermission(plugin.getPermission("reload"));
        withDescription(plugin.getLang("reload-command"));
        withCommandExecution((sender, args, placeholders) -> {
            try {
                plugin.reload(errorsFound -> {
                    if (sender instanceof Player player) {
                        ConfigErrorLogger.logAll(plugin, player, errorsFound);
                    }
                    else {
                        ConfigErrorLogger.logAll(plugin, errorsFound);
                    }
                    plugin.sendMessage(sender, "reload-complete", placeholders);
                });
            }
            catch (PluginReloadInProgressException e) {
                plugin.sendMessage(sender, "already-reloading", placeholders);
                return;
            }
            plugin.sendMessage(sender, "reloading", placeholders);
        });
    }


}
