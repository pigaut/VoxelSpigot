package io.github.pigaut.voxel.message.type;

import io.github.pigaut.voxel.message.*;
import io.github.pigaut.voxel.meta.placeholder.*;
import io.github.pigaut.voxel.plugin.*;
import org.bukkit.entity.*;

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
    public void send(Player player, PlaceholderSupplier... placeholderSuppliers) {
        plugin.getScheduler().runTaskLater(delay, () -> {
            message.send(player, placeholderSuppliers);
        });
    }

}
