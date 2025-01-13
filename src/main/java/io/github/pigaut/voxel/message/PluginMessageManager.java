package io.github.pigaut.voxel.message;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.node.section.*;

import java.io.*;

public class PluginMessageManager extends MessageManager {

    private final EnhancedPlugin plugin;

    public PluginMessageManager(EnhancedPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void disable() {
        clearMessages();
    }

    @Override
    public void load() {
        for (File langFile : plugin.getFiles("messages")) {
            final RootSection config = new RootSection(langFile, plugin.getConfigurator());
            config.load();

            for (String key : config.getKeys()) {
                final Message message = config.get(key, Message.class);
                addMessage(key, message);
            }
        }
    }

}
