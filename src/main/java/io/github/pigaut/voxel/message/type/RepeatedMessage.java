package io.github.pigaut.voxel.message.type;

import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import org.bukkit.entity.*;

public class RepeatedMessage implements Message {

    private final Message message;
    private final int repetitions;

    public RepeatedMessage(Message message, int repetitions) {
        this.message = message;
        this.repetitions = repetitions;
    }

    @Override
    public void send(Player player, PlaceholderSupplier... placeholderSuppliers) {
        for (int i = 0; i < repetitions; i++) {
            message.send(player, placeholderSuppliers);
        }
    }

}
