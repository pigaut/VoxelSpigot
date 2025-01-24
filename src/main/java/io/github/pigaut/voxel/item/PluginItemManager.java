package io.github.pigaut.voxel.item;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.section.*;
import org.bukkit.inventory.*;

import java.io.*;

public class PluginItemManager extends ItemManager {

    public PluginItemManager(EnhancedPlugin plugin) {
        super(plugin);
    }

    @Override
    public void loadData() {
        clearItems();
        for (File itemFile : plugin.getFiles("items")) {
            final ConfigSection config = ConfigSection.loadConfiguration(itemFile, plugin.getConfigurator());
            for (String key : config.getKeys()) {
                final ItemStack item = config.get(key, ItemStack.class);
                addItemStack(key, item);
            }
        }
    }

}
