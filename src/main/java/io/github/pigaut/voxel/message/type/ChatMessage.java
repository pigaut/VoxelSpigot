package io.github.pigaut.voxel.message.type;

import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import io.github.pigaut.voxel.util.*;
import org.bukkit.entity.*;

public class ChatMessage implements Message {

    private final String message;

    public ChatMessage(String message) {
        this.message = message;
    }

    @Override
    public void send(Player player) {
        Chat.send(player, message);
    }

    @Override
    public void send(Player player, PlaceholderSupplier... placeholderSuppliers) {
        Chat.send(player, message, placeholderSuppliers);
    }

}
