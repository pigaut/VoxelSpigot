package io.github.pigaut.voxel.core.message;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;
import io.github.pigaut.yaml.node.section.*;
import org.jetbrains.annotations.*;

import java.io.*;

public class MessageManager extends ConfigBackedManager.SectionKey<Message> {

    public MessageManager(EnhancedJavaPlugin plugin) {
        super(plugin, "Message", "messages");
    }

    @Override
    public void loadFromSectionKey(ConfigSection section, String key) throws InvalidConfigurationException {
        final Message message = section.getRequired(key, Message.class);
        try {
            add(message);
        }
        catch (DuplicateElementException e) {
            throw new InvalidConfigurationException(section, key, e.getMessage());
        }
    }

}
