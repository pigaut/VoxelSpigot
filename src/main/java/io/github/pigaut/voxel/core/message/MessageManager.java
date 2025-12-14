package io.github.pigaut.voxel.core.message;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;
import io.github.pigaut.yaml.*;

public class MessageManager extends ConfigBackedManager.SectionKey<Message> {

    public MessageManager(EnhancedJavaPlugin plugin) {
        super(plugin, "messages");
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
