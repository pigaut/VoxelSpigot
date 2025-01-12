package io.github.pigaut.voxel.message.type;

import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import org.bukkit.entity.*;

import java.util.*;

public class SequenceMessage implements Message {

    private final List<Message> messages;

    public SequenceMessage(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public void send(Player user) {
        for (Message message : messages)
            message.send(user);
    }

    @Override
    public void send(Player player, PlaceholderSupplier... placeholderSuppliers) {
        
    }

}
