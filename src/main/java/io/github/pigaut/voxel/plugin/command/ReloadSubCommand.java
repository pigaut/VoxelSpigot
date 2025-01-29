package io.github.pigaut.voxel.plugin.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class ReloadSubCommand extends LangSubCommand {

    public ReloadSubCommand(@NotNull EnhancedJavaPlugin plugin) {
        super("reload", plugin);
        withCommandExecution((sender, args, placeholders) -> {
            plugin.sendMessage(sender, "reloading", placeholders);
            plugin.createHooks();
            plugin.generateFiles();
            final List<Manager> loadedManagers = plugin.getLoadedManagers();
            for (Manager manager : loadedManagers) {
                manager.disable();
                manager.enable();
            }
            plugin.getScheduler().runTaskAsync(() -> {
                try {
                    plugin.getLogger().info("Saving data to database...");
                    loadedManagers.forEach(Manager::saveData);
                    plugin.getLogger().info("Loading configuration and data...");
                    loadedManagers.forEach(Manager::loadData);
                } catch (ConfigurationLoadException | InvalidConfigurationException e) {
                    if (sender instanceof Player) {
                        sender.sendMessage(ChatColor.RED + e.getMessage());
                    }
                    for (Manager manager : loadedManagers) {
                        manager.disable();
                    }
                    throw e;
                }
                plugin.sendMessage(sender, "reload-complete", placeholders);
            });
        });
    }


}
