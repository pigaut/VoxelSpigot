package io.github.pigaut.voxel.message.type;

import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class MultiMessage implements Message {

    private final List<Message> messages;

    public MultiMessage(@NotNull List<@NotNull Message> messages) {
        this.messages = messages;
    }

    @Override
    public void send(Player player, PlaceholderSupplier... placeholderSuppliers) {
        for (Message message : messages) {
            message.send(player, placeholderSuppliers);
        }
    }

}
