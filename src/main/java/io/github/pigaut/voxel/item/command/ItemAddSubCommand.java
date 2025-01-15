package io.github.pigaut.voxel.item.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.section.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.*;

public class ItemAddSubCommand extends SubCommand {

    public ItemAddSubCommand(@NotNull EnhancedPlugin plugin) {
        super(plugin.getLang("ADD_ITEM_COMMAND", "add"), plugin);
        withDescription(plugin.getLang("ADD_ITEM_DESCRIPTION"));
        addParameter(plugin.getLang("FILE_PATH_PARAMETER", "file-path"));
        addParameter(plugin.getLang("ITEM_NAME_PARAMETER", "item-name"));
        withPlayerExecution((player, args) -> {
            final ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType() == Material.AIR) {
                plugin.sendMessage(player, "NOT_HOLDING_ITEM");
                return;
            }

            final File file = plugin.getFile(args[0]);
            if (!YamlConfig.isYamlFile(file)) {
                plugin.sendMessage(player, "NOT_YAML_FILE");
                return;
            }

            final RootSection config = new RootSection(file, plugin.getConfigurator());
            plugin.getScheduler().runTaskAsync(() -> {
                config.load();
                config.set(args[1], item);
                config.save();
                plugin.getItems().reload();
            });
            plugin.sendMessage(player, "ADDING_ITEM");
        });

        withPlayerCompletion((player, args) -> {
           if (args.length < 2) {
               return plugin.getFiles("items").stream()
                       .map(file -> file.getPath().replaceAll("plugins\\\\[^\\\\]+\\\\", ""))
                       .toList();
           }
           if (args.length == 2) {
               return plugin.getItems().getItemNames();
           }
           return List.of();
        });

    }

}
