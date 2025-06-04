package io.github.pigaut.voxel.core.item.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.command.parameter.*;
import io.github.pigaut.voxel.core.item.command.parameter.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.section.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.io.*;

public class ItemAddSubCommand extends SubCommand {

    public ItemAddSubCommand(@NotNull EnhancedPlugin plugin) {
        super("add", plugin);
        withPermission(plugin.getPermission("item.add"));
        withDescription(plugin.getLang("item-add-command"));
        addParameter(new FilePathParameter(plugin, "items"));
        addParameter(new ItemNameParameter(plugin));
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

            final RootSection config = new RootSection(file, plugin.getConfigurator());
            plugin.getScheduler().runTaskAsync(() -> {
                config.load();
                config.set(args[1], item);
                config.save();
                plugin.getItems().reload();
            });
            plugin.sendMessage(player, "adding-item", placeholders);
        });
    }

}
