package io.github.pigaut.voxel.core.message;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.section.*;
import org.jetbrains.annotations.*;

import java.io.*;

public class MessageManager extends ManagerContainer<Message> {

    public MessageManager(EnhancedJavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public @Nullable String getFilesDirectory() {
        return "messages";
    }

    @Override
    public void loadFile(@NotNull File file) {
        final RootSection config = new RootSection(file, plugin.getConfigurator());
        config.setPrefix("Message");
        config.load();

        for (String messageName : config.getKeys()) {
            final Message message = config.get(messageName, Message.class);
            try {
                add(message);
            } catch (DuplicateElementException e) {
                throw new InvalidConfigurationException(config, messageName, e.getMessage());
            }
        }
    }

}
