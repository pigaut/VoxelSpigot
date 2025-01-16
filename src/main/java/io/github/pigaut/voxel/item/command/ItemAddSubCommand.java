package io.github.pigaut.voxel.item.command;

import io.github.pigaut.voxel.command.node.*;
import io.github.pigaut.voxel.command.parameter.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.section.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.io.*;

public class ItemAddSubCommand extends LangSubCommand {

    public ItemAddSubCommand(@NotNull EnhancedPlugin plugin) {
        super("add-item", plugin);
        addParameter(new FilePathParameter(plugin, "items"));
        addParameter(new ItemNameParameter(plugin));
        withPlayerExecution((player, args, placeholders) -> {
            final ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType() == Material.AIR) {
                plugin.sendMessage(player, "NOT_HOLDING_ITEM", placeholders);
                return;
            }
            final File file = plugin.getFile(args[0]);
            if (!YamlConfig.isYamlFile(file)) {
                plugin.sendMessage(player, "NOT_YAML_FILE", placeholders);
                return;
            }
            final RootSection config = new RootSection(file, plugin.getConfigurator());
            plugin.getScheduler().runTaskAsync(() -> {
                config.load();
                config.set(args[1], item);
                config.save();
                plugin.getItems().reload();
            });
            plugin.sendMessage(player, "ADDING_ITEM", placeholders);
        });
    }

}
