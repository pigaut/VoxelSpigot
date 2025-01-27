package io.github.pigaut.voxel.message;

import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.voxel.plugin.manager.*;

import java.util.*;

public class MessageManager extends Manager {

    private final Map<String, Message> messagesByName = new HashMap<>();

    public MessageManager(EnhancedPlugin plugin) {
        super(plugin);
    }

    public List<String> getMessageNames() {
        return new ArrayList<>(messagesByName.keySet());
    }

    public Message getMessage(String name) {
        return messagesByName.get(name);
    }

    public Message addMessage(String name, Message message) {
        return messagesByName.put(name, message);
    }

    public Message removeMessage(String name) {
        return messagesByName.remove(name);
    }

    public void clearMessages() {
        messagesByName.clear();
    }

}
