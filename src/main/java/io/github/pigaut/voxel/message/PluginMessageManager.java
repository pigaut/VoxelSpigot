package io.github.pigaut.voxel.message;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.section.*;

import java.io.*;

public class PluginMessageManager extends MessageManager {

    public PluginMessageManager(EnhancedPlugin plugin) {
        super(plugin);
    }

    @Override
    public void loadData() {
        clearMessages();
        for (File langFile : plugin.getFiles("messages")) {
            final ConfigSection config = plugin.loadConfigSection(langFile);
            for (String key : config.getKeys()) {
                final Message message = config.get(key, Message.class);
                addMessage(key, message);
            }
        }
    }

}
