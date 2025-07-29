package io.github.pigaut.voxel.core.message.impl;

import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.yaml.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class RepeatedMessage implements Message {

    private final Message message;
    private final int repetitions;

    public RepeatedMessage(Message message, int repetitions) {
        this.message = message;
        this.repetitions = repetitions;
    }

    @Override
    public @NotNull String getName() {
        return message.getName();
    }

    @Override
    public @Nullable String getGroup() {
        return message.getGroup();
    }

    @Override
    public @NotNull MessageType getType() {
        return message.getType();
    }

    @Override
    public @NotNull ItemStack getIcon() {
        return message.getIcon();
    }

    @Override
    public @NotNull ConfigField getField() {
        return message.getField();
    }

    @Override
    public void send(@NotNull Player player, PlaceholderSupplier... placeholderSuppliers) {
        for (int i = 0; i < repetitions; i++) {
            message.send(player, placeholderSuppliers);
        }
    }

}
