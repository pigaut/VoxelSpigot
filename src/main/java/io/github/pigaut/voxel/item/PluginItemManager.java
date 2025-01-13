package io.github.pigaut.voxel.item;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.node.section.*;
import org.bukkit.inventory.*;

import java.io.*;

public class PluginItemManager extends ItemManager {

    private final EnhancedPlugin plugin;

    public PluginItemManager(EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void disable() {
        clearItems();
    }

    @Override
    public void load() {
        for (File itemFile : plugin.getFiles("items")) {
            final RootSection config = new RootSection(itemFile, plugin.getConfigurator());
            config.load();
            for (String key : config.getKeys()) {
                final ItemStack item = config.get(key, ItemStack.class);
                addItemStack(key, item);
            }
        }
    }

}
