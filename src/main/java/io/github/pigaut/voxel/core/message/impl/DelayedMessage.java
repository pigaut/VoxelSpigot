package io.github.pigaut.voxel.core.message.impl;

import io.github.pigaut.voxel.core.message.*;
import io.github.pigaut.voxel.placeholder.*;
import io.github.pigaut.voxel.plugin.*;
import io.github.pigaut.yaml.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class DelayedMessage implements Message {

    private final EnhancedPlugin plugin;
    private final Message message;
    private final int delay;

    public DelayedMessage(EnhancedPlugin plugin, Message message, int delay) {
        this.plugin = plugin;
        this.message = message;
        this.delay = delay;
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
    public void send(@NotNull Player player, PlaceholderSupplier... placeholderSuppliers) {
        plugin.getScheduler().runTaskLater(delay, () -> {
            message.send(player, placeholderSuppliers);
        });
    }

}
