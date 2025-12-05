package io.github.pigaut.voxel.core.item.command;

import io.github.pigaut.voxel.command.*;
import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.section.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.io.*;

public class ItemSaveSubCommand extends SubCommand {

    public ItemSaveSubCommand(@NotNull EnhancedPlugin plugin) {
        super("save", plugin);
        withPermission(plugin.getPermission("item.save"));
        withDescription(plugin.getTranslation("item-save-command"));
        withParameter(CommandParameters.filePath(plugin, "items"));
        withParameter(CommandParameters.itemName(plugin));
        withPlayerExecution((player, args, placeholders) -> {
            final ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType() == Material.AIR) {
                plugin.sendMessage(player, "not-holding-item", placeholders);
                return;
            }

            final File file = plugin.getFile("items", args[0]);
            if (!file.exists()) {
                plugin.sendMessage(player, "file-not-found", placeholders);
                return;
            }

            if (!YamlConfig.isYamlFile(file)) {
                plugin.sendMessage(player, "not-yaml-file", placeholders);
                return;
            }

            final RootSection config = YamlConfig.loadSection(file, plugin.getConfigurator());
            plugin.getScheduler().runTaskAsync(() -> {
                config.load();
                config.set(args[1], item);
                config.save();
                plugin.getItems().reload();
                plugin.sendMessage(player, "saved-item", placeholders);
            });
        });
    }

}
